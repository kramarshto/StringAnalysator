import java.util.Objects;
/**
 * This class allows you to work with data by structuring it into a unidirectional list
 *
 * @param <T> type of val stored in the list
 */
public class Container<T> {

    /**
     * Inner class describing list element
     */
    class ListElement {
        private ListElement next;
        private final String ch;
        private final T data;

        {
            next = null;
        }

        /**
         * constructor with data
         *
         * @param data data added to head type T, ch added to head type String
         */
        public ListElement(T data, String ch) {
            this.data = data;
            this.ch = ch;
        }
    }

    private ListElement head;
    private ListElement tail;

    /**
     * default constructor
     */
    public Container() {
        head = null;
    }

    /**
     * constructor with data
     *
     * @param elem element added to head type T, ch added to head type String
     */
    public Container(T elem, String ch) {
        head = new ListElement(elem, ch);
        tail = head;
    }

    /**
     * check for emptiness
     *
     * @return 1 is empty
     * 0 is not empty
     */
    boolean isEmpty() {
        return head == null;
    }

    /**
     * add element to end
     *
     * @param elem element type T, ch type String
     */
    void add(T elem, String ch) {
        ListElement p = new ListElement(elem, ch);
        if (tail == null) {
            head = p;
        } else {
            tail.next = p;
        }
        tail = p;
    }

    /**
     * add element to head
     *
     * @param elem element type T, ch type String
     */
    void addToHead(T elem, String ch) {
        ListElement p = new ListElement(elem, ch);
        if (isEmpty()) {
            head = p;
            tail = p;
        } else {
            p.next = head;
            head = p;
        }
    }

    /**
     * add element after node
     *
     * @param pNode node of element
     */
    void addAfterNode(ListElement pNode, T elem, String ch) {
        ListElement p = new ListElement(elem, ch);
        p.next = pNode.next;
        pNode.next = p;
    }

    /**
     * find place before element
     *
     * @param ch element type T
     * @return ListElement
     * node of element
     */
    ListElement findPlaceBefore(String ch) {
        ListElement p = head;
        while ((p.next != null) && (!Objects.equals(p.next.ch, ch)))
            p = p.next;
        return p;
    }

    /**
     * find place of element
     *
     * @param ch element type String
     * @return ListElement
     * node of element
     */
    ListElement findPlace(String ch) {
        ListElement p = head;
        while ((p.next != null) && (!Objects.equals(p.ch, ch)))
            p = p.next;
        return p;
    }

    /**
     * get element by index
     *
     * @param ch index of element type int
     * @return elem type T
     */
    T get(String ch) {
        T result = null;
        if (Objects.equals(ch, head.ch))
            result = head.data;
        else {
            ListElement p = head.next;
            while ((p != null) && (p.next != null) && (!Objects.equals(p.ch, ch)))
                p = p.next;
            if (p != null)
                result = p.data;
        }
        return result;
    }

    /**
     * delete element
     *
     * @param ch deleted element type String
     */
    void delete(String ch) {
        if (Objects.equals(head.ch, ch))
            deleteFromHead();
        else
            deleteAfterNode(findPlaceBefore(ch));
    }

    /**
     * delete element from head
     */
    void deleteFromHead() {
        head = head.next;
    }

    /**
     * delete element after node
     */
    void deleteAfterNode(ListElement pNode) {
        ListElement p = pNode.next;
        pNode.next = p.next;
    }

    /**
     * Check containers on equality
     *
     * @param container object of container
     * @return 0 if not equal
     * 1 if equal
     */
    public boolean equals(Container<T> container) {
        if (isEmpty() && container.isEmpty())
            return true;
        else {
            ListElement node = head;
            ListElement node2 = container.head;
            while (node != null && node2 != null) {
                if (!node.data.equals(node2.data) && !node.ch.equals(node2.ch))
                    return false;
                node = node.next;
                node2 = node2.next;
            }
            return node == null && node2 == null;
        }
    }

    /**
     * Check containers on equality
     *
     * @return string
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        ListElement p = head;
        while (p != null) {
            if (p.next != null) {
                s.append(p.ch).append(" = ").append(p.data).append(" -> ");
                p = p.next;
            } else {
                s.append(p.ch).append(" = ").append(p.data);
                p = null;
            }
        }
        return s.toString();
    }
}