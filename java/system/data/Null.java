package system.data;

/**
 * {@code Null} classifies null data types.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Null
{
    /**
     * {@code XMLNode} classifies a null XML node.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface XMLNode
    extends org.w3c.dom.Node
    {
        @Override
        public default String getNodeName() {
            return null;
        }

        @Override
        public default String getNodeValue() throws org.w3c.dom.DOMException {
            return null;
        }

        @Override
        public default void setNodeValue(String nodeValue) throws org.w3c.dom.DOMException {}

        @Override
        public default short getNodeType() {
            return 0;
        }

        @Override
        public default org.w3c.dom.Node getParentNode() {
            return null;
        }

        @Override
        public default org.w3c.dom.NodeList getChildNodes() {
            return null;
        }

        @Override
        public default org.w3c.dom.Node getFirstChild() {
            return null;
        }

        @Override
        public default org.w3c.dom.Node getLastChild() {
            return null;
        }

        @Override
        public default org.w3c.dom.Node getPreviousSibling() {
            return null;
        }

        @Override
        public default org.w3c.dom.Node getNextSibling() {
            return null;
        }

        @Override
        public default org.w3c.dom.NamedNodeMap getAttributes() {
            return null;
        }

        @Override
        public default org.w3c.dom.Document getOwnerDocument() {
            return null;
        }

        @Override
        public default org.w3c.dom.Node insertBefore(org.w3c.dom.Node newChild, org.w3c.dom.Node refChild) throws org.w3c.dom.DOMException {
            return null;
        }

        @Override
        public default org.w3c.dom.Node replaceChild(org.w3c.dom.Node newChild, org.w3c.dom.Node oldChild) throws org.w3c.dom.DOMException {
            return null;
        }

        @Override
        public default org.w3c.dom.Node removeChild(org.w3c.dom.Node oldChild) throws org.w3c.dom.DOMException {
            return null;
        }

        @Override
        public default org.w3c.dom.Node appendChild(org.w3c.dom.Node newChild) throws org.w3c.dom.DOMException {
            return null;
        }

        @Override
        public default boolean hasChildNodes() {
            return false;
        }

        @Override
        public default org.w3c.dom.Node cloneNode(boolean deep) {
            return null;
        }

        @Override
        public default void normalize() {}

        @Override
        public default boolean isSupported(String feature, String version) {
            return false;
        }

        @Override
        public default String getNamespaceURI() {
            return null;
        }

        @Override
        public default String getPrefix() {
            return null;
        }

        @Override
        public default void setPrefix(String prefix) throws org.w3c.dom.DOMException {}

        @Override
        public default String getLocalName() {
            return null;
        }

        @Override
        public default boolean hasAttributes() {
            return false;
        }

        @Override
        public default String getBaseURI() {
            return null;
        }

        @Override
        public default short compareDocumentPosition(org.w3c.dom.Node other) throws org.w3c.dom.DOMException {
            return 0;
        }

        @Override
        public default String getTextContent() throws org.w3c.dom.DOMException {
            return null;
        }

        @Override
        public default void setTextContent(String textContent) throws org.w3c.dom.DOMException {}

        @Override
        public default boolean isSameNode(org.w3c.dom.Node other) {
            return false;
        }

        @Override
        public default String lookupPrefix(String namespaceURI) {
            return null;
        }

        @Override
        public default boolean isDefaultNamespace(String namespaceURI) {
            return false;
        }

        @Override
        public default String lookupNamespaceURI(String prefix) {
            return null;
        }

        @Override
        public default boolean isEqualNode(org.w3c.dom.Node arg) {
            return false;
        }

        @Override
        public default Object getFeature(String feature, String version) {
            return null;
        }

        @Override
        public default Object setUserData(String key, Object data, org.w3c.dom.UserDataHandler handler) {
            return null;
        }

        @Override
        public default Object getUserData(String key) {
            return null;
        }
    }
}