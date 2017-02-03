/*
 * Copyright 2014, Jaime Bárez Lobato - jaimebarez@gmail.com. All rights reserved.
 * Granted copying and distribution rights for internal, commercial and 
 * not commercial use.
 *
 *
 */
package jbarezlibs.xml;
//dd/MM/YYYY
//15/09/2014

import java.util.Iterator;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Used to iterate easily through XML nodes
 *
 * @author Jaime Bárez Lobato - jaimebarez@gmail.com
 */
public class NodeListIterable implements Iterable<Node>, NodeList {

    private final NodeList nl;

    public NodeListIterable(Node d, XPathExpression path) {
        try {
            this.nl = (NodeList) path.evaluate(d, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            throw new RuntimeException("XPathExpressionException", e);
        }
    }

    public NodeListIterable(NodeList nl) {
        if (nl == null) {
            throw new NullPointerException("Node list invalid");
        }
        this.nl = nl;
    }

    @Override
    public Iterator<Node> iterator() {
        return new Iterator<Node>() {
            int i = 0;
            int last = nl.getLength();

            @Override
            public boolean hasNext() {
                return i < last;
            }

            @Override
            public Node next() {
                return nl.item(i++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Cannot remove");
            }

        };
    }

    @Override
    public int getLength() {
        if (nl == null) {
            return 0;
        }
        return nl.getLength();
    }

    @Override
    public Node item(int index) {
        if (nl == null) {
            return null;
        }
        return nl.item(index);
    }
}
