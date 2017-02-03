/*
 * Copyright 2014, Jaime Bárez Lobato - jaimebarez@gmail.com. All rights reserved.
 * Granted copying and distribution rights for internal, commercial and 
 * not commercial use.
 *
 *
 */
package jbarezlibs.utilities.javafx;
//dd/MM/YYYY
//15/09/2014

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.DoubleExpression;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import jbarezlibs.bindings.numerical.NumberBindings;
import jbarezlibs.bindings.WrappedChildBinding;
import jbarezlibs.bindings.lists.ListChangeListenerHelperAdapter;
import jbarezlibs.utilities.Disposable;

/**
 * Views avobe and down the viewport will change their visibility state to
 * false. Content of the scrollpane must be region to work well
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 *
 */
public class EfficientScrollPane extends ScrollPane {

    private final DoubleBinding topViewYCoordinate;
    private final DoubleBinding bottomViewYCoordinate;
    private final DoubleExpression parentHeightProperty;
    private final Observable[] dependencies;
    private final ObservableList<Node> childrenToObserve;

    public EfficientScrollPane() {
        this.childrenToObserve = FXCollections.observableArrayList();
        contentProperty().addListener(new ChangeListener<Node>() {

            @Override
            public void changed(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
                if (oldValue != null && oldValue instanceof Region) {
                    Bindings.unbindContent(childrenToObserve, ((Region) oldValue).getChildrenUnmodifiable());
                }
                childrenToObserve.clear();

                if (newValue != null && newValue instanceof Region) {
                    Bindings.bindContent(childrenToObserve, ((Region) newValue).getChildrenUnmodifiable());
                }
            }
        });

        parentHeightProperty = NumberBindings.createDoubleBinding(new WrappedChildBinding<Node, Number>(contentProperty()) {

            @Override
            protected ObservableValue<Number> getObservableChildValue(Node notNullParentWrappedValue) {
                if (notNullParentWrappedValue instanceof Region) {
                    return ((Region) notNullParentWrappedValue).heightProperty();
                }
                return null;
            }
        }, 0);

        topViewYCoordinate = vvalueProperty().multiply(parentHeightProperty.subtract(heightProperty()));
        bottomViewYCoordinate = topViewYCoordinate.add(heightProperty());

        dependencies = new Observable[]{topViewYCoordinate, bottomViewYCoordinate, parentHeightProperty, childrenToObserve};

        ListChangeListenerVisibilities listChangeListenerVisibilities = new ListChangeListenerVisibilities();
        childrenToObserve.addListener(listChangeListenerVisibilities);
        listChangeListenerVisibilities.reInitializate();

    }

    private class ListChangeListenerVisibilities extends ListChangeListenerHelperAdapter<Node> {

        private final HashMap<Node, BooleanBinding> nodeBindingMap = new HashMap<>();
        private final BindsPropsListnrsDisposerHelper dHelper = new BindsPropsListnrsDisposerHelper();

        @Override
        public void reInitializate() {
            int i = 0;
            for (Node node : childrenToObserve) {
                removed(i++, node);
            }
            for (Node node : childrenToObserve) {
                added(i++, node);
            }
        }

        @Override
        protected void added(int from, Node element) {
            BooleanBinding nodeEfficientVisibleProperty = new NodeEfficientVisibleProperty(element);
            dHelper.addAndPlatformRunLaterListen(element.visibleProperty(), nodeEfficientVisibleProperty);
            nodeBindingMap.put(element, nodeEfficientVisibleProperty);
        }

        @Override
        protected void removed(int from, Node element) {
            element.visibleProperty().unbind();
            dHelper.unListen(element.visibleProperty());
            BooleanBinding bBinding = nodeBindingMap.remove(element);
            if (bBinding != null) {
                bBinding.dispose();
            }
        }
    }

    private class NodeEfficientVisibleProperty extends BooleanBinding implements Disposable {

        private final Node element;
        private final List<Observable> myDependencies;

        public NodeEfficientVisibleProperty(Node element_) {
            this.element = element_;
            myDependencies = new ArrayList<>(Arrays.asList(dependencies));
            myDependencies.add(element.boundsInParentProperty());
            bind(myDependencies.toArray(new Observable[myDependencies.size()]));
        }

        @Override
        protected boolean computeValue() {
            Bounds boundsInParent = element.getBoundsInParent();
            boolean visible = boundsInParent.getMaxY() >= topViewYCoordinate.get()
                    && boundsInParent.getMinY() <= (bottomViewYCoordinate.get());
            return visible;
        }

        @Override
        public final ObservableList<?> getDependencies() {
            return FXCollections.observableArrayList(myDependencies);
        }

        @Override
        public void dispose() {
            super.dispose();
            unbind(myDependencies.toArray(new Observable[myDependencies.size()]));
        }
    }
}
