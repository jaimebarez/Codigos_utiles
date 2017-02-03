/*
 * Copyright 2014, Jaime Bárez Lobato - jaimebarez@gmail.com. All rights reserved.
 * Granted copying and distribution rights for internal, commercial and 
 * not commercial use.
 *
 *
 */
package jbarezlibs.bindings.lists;
//dd/MM/YYYY
//12/09/2014

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javafx.collections.ObservableList;

/**
 * Helps developers to create a list of nodes according to list changes. It
 * creates a map that has an internal node for each list element.
 *
 * @author Jaime Bárez Lobato
 * @param <E> // Class of the items of the list
 * @param <MyNode>
 */
public abstract class ListChangeListenerNodes<E, MyNode>
        extends ListChangeListenerHelper<E> {

    private final Map<E, MyNode> hashElemNode;
    private final List<E> elementsList;

    /**
     * To provide a correct behaviour, elementsList must be the same list we
     * listen to.
     *
     * @param elementsList
     */
    public ListChangeListenerNodes(ObservableList<E> elementsList) {
        this.hashElemNode = new HashMap<>();
        this.elementsList = elementsList;

    }

    /**
     * Given a list element, we return the internal node
     *
     * @param element
     * @return
     */
    public MyNode getInternalNode(E element) {
        return hashElemNode.get(element);
    }

    /**
     * Removes and adds needed nodes.
     */
    @Override
    public final void reInitializate() {
        List<E> nodesListElements
                = new LinkedList<>(hashElemNode.keySet());
        {
            int i = 0;
            for (E element : nodesListElements) {
                removed(i, element);
                i++;
            }
        }
        {
            int i = 0;
            for (E element : elementsList) {
                added(i, element);
                i++;
            }
        }
    }

    /**
     * Creates an internal node, adds it to the internal map and notifies that
     * new node was created.
     *
     * @param index
     * @param element
     */
    @Override
    protected final void added(int index, E element) {
        // System.out.println("added " + this);
        final MyNode myNode = createNode(index, element);

        if (!hashElemNode.containsKey(element)) {
            hashElemNode.put(element, myNode);
            nodeAdded(myNode, index, element);
        } else {
// TODO           System.err.println(myNode);
//            new Exception("PROGRAMMING BUG").printStackTrace();
        }
    }

    /**
     * Removes the corresponding internal node and notifies removal.
     *
     * @param from
     * @param element
     */
    @Override
    protected final void removed(int from, E element) {

        MyNode remove = hashElemNode.remove(element);
        if (remove != null) {
            nodeRemoved(remove, from, element);
        }
    }

    /**
     * Reinitializates
     */
    @Override
    protected final void permutated() {
        reInitializate();
    }

    /**
     * Extending class will override it to create a node given a list element
     * and its index
     *
     * @param index
     * @param element
     * @return
     */
    protected abstract MyNode createNode(int index, E element);

    /**
     * Called when an internal node is added.
     *
     * @param myNode
     * @param index
     * @param elementAdded
     */
    protected abstract void nodeAdded(MyNode myNode, int index, E elementAdded);

    /**
     * Called when an internal node is removed.
     *
     * @param myNode
     * @param index
     * @param elementRemoved
     */
    protected abstract void nodeRemoved(MyNode myNode, int index, E elementRemoved);

}
