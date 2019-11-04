package duke.model.transports;

import duke.commons.exceptions.NullResultException;
import duke.commons.exceptions.OutOfBoundsException;
import duke.commons.exceptions.DuplicateRouteNodeException;
import duke.model.locations.RouteNode;
import duke.model.locations.Venue;

import java.util.ArrayList;

/**
 * Represents a route between 2 locations.
 */
public class Route {
    private ArrayList<RouteNode> nodes;
    private String name;
    private String description;

    /**
     * Constructs an empty route object.
     *
     * @param name The name of the route.
     * @param description The description of the route.
     */
    public Route(String name, String description) {
        this.nodes = new ArrayList<>();
        this.name = name;
        this.description = description;
    }

    /**
     * Alternative constructor with predefined nodes.
     *
     * @param nodes The nodes of the route.
     * @param name The name of the route.
     * @param description The description of the route.
     */
    public Route(ArrayList<RouteNode> nodes, String name, String description) {
        this.nodes = nodes;
        this.name = name;
        this.description = description;
    }

    /**
     * Adds a new node to the route at a given index.
     *
     * @param newNode The new node to add.
     * @param index The index of the node to add to.
     * @exception DuplicateRouteNodeException If the route is a duplicate.
     * @exception OutOfBoundsException If the index is out of bounds.
     */
    public void addNode(RouteNode newNode, int index) throws DuplicateRouteNodeException, OutOfBoundsException {
        if (index >= 0 && index <= nodes.size()) {
            for (RouteNode node : nodes) {
                if (node.equals(newNode)) {
                    throw new DuplicateRouteNodeException();
                }
            }
            nodes.add(index, newNode);
            return;
        }

        throw new OutOfBoundsException();
    }

    /**
     * Alternate method to add a node at the end of the Route.
     *
     * @param newNode The new node to add.
     * @exception DuplicateRouteNodeException If the route is a duplicate.
     */
    public void add(RouteNode newNode) throws DuplicateRouteNodeException {
        for (RouteNode node: nodes) {
            if (node.equals(newNode)) {
                throw new DuplicateRouteNodeException();
            }
        }

        nodes.add(newNode);
    }

    /**
     * Deletes the node at a given index.
     *
     * @param index The index of the node to delete.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    public void remove(int index) throws IndexOutOfBoundsException {
        nodes.remove(index);
    }

    /**
     * Gets the node at a given index.
     *
     * @param index The index of the node.
     * @return node The node at the index.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    public RouteNode getNode(int index) throws IndexOutOfBoundsException {
        try {
            return nodes.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Gets a node with the given name.
     *
     * @param name The query name.
     * @return node The queried node.
     * @throws NullResultException If nothing is found.
     */
    public RouteNode getNodeByName(String name) throws NullResultException {
        for (RouteNode node: nodes) {
            String nodeName = node.getAddress().toLowerCase();
            if (nodeName.equals(name.toLowerCase())) {
                return node;
            }
        }

        throw new NullResultException();
    }

    /**
     * Gets the Arraylist of Route Nodes.
     *
     * @return nodes The ArrayList of Route Nodes.
     */
    public ArrayList<RouteNode> getNodes() {
        return nodes;
    }

    /**
     * Gets the Arraylist of Route Nodes as Venues instead.
     *
     * @return nodes The ArrayList of Route Nodes.
     */
    public ArrayList<Venue> getNodesAsVenue() {
        return new ArrayList<>(nodes);
    }

    /**
     * Gets the starting node of the route.
     *
     * @return node The start node.
     */
    public RouteNode getStartNode() {
        if (nodes.size() > 0) {
            return nodes.get(0);
        } else {
            return null;
        }
    }

    /**
     * Gets the starting node of the route.
     *
     * @return node The start node.
     */
    public RouteNode getEndNode() {
        if (nodes.size() > 0) {
            return nodes.get(nodes.size() - 1);
        } else {
            return null;
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setNodes(ArrayList<RouteNode> nodes) {
        this.nodes = nodes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the size of the Route.
     *
     * @return The size of the Route.
     */
    public int size() {
        return nodes.size();
    }

    /**
     * Returns true if both routes are the same.
     */
    public boolean isSameRoute(Route otherRoute) {
        if (otherRoute == this) {
            return true;
        }

        return otherRoute != null && otherRoute.getName().equals(getName())
                && otherRoute.getDescription().equals(getDescription()) && otherRoute.getNodes().equals(nodes);
    }
}
