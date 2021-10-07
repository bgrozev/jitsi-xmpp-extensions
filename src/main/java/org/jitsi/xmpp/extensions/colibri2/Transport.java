package org.jitsi.xmpp.extensions.colibri2;

import org.jitsi.xmpp.extensions.*;
import org.jitsi.xmpp.extensions.jingle.*;

import javax.xml.namespace.*;

public class Transport
    extends AbstractPacketExtension
{
    /**
     * The XML element name of the Colibri2 Transport element.
     */
    public static final String ELEMENT = "transport";

    /**
     * The XML COnferencing with LIghtweight BRIdging namespace of the Jitsi
     * Videobridge <tt>conference-modify</tt> IQ.
     */
    public static final String NAMESPACE = ConferenceModifyIQ.NAMESPACE;

    /**
     * The qualified name of the element.
     */
    public static final QName QNAME = new QName(NAMESPACE, ELEMENT);

    /**
     * The name of the <tt>use-unique-port</tt> attribute.
     */
    public static final String USE_UNIQUE_PORT_ATTR_NAME = "use-unique-port";

    /**
     * Construct a Transport.  Needs to be public for DefaultPacketExtensionProvider to work.
     */
    public Transport()
    {
        super(NAMESPACE, ELEMENT);
    }

    /**
     * Construct a transport from a builder - used by Builder#build().
     */
    private Transport(Builder b)
    {
        super(NAMESPACE, ELEMENT);

        if (b.useUniquePort) {
            super.setAttribute(USE_UNIQUE_PORT_ATTR_NAME, true);
        }

        if (b.iceUdpExtension != null) {
            super.addChildExtension(b.iceUdpExtension);
        }
    }

    /**
     * Creates an {@link AbstractPacketExtension} instance for the specified
     * <tt>namespace</tt> and <tt>elementName</tt>.
     *
     * @param namespace   the XML namespace for this element.
     * @param elementName the name of the element
     */
    protected Transport(String namespace, String elementName)
    {
        super(namespace, elementName);
    }

    /**
     * Gets whether a unique candidate port should be used.  Only meaningful
     * in a conference-modify request.
     */
    public boolean getUseUniquePort()
    {
        Object use = super.getAttribute(USE_UNIQUE_PORT_ATTR_NAME);
        if (use instanceof Boolean)
        {
            return (Boolean) use;
        }
        else if (use instanceof String)
        {
            return Boolean.parseBoolean((String) use);
        }
        return false;
    }

    /**
     * Return the contained ICE UDP Transport object, or null.
     */
    public IceUdpTransportPacketExtension getIceUdpTransport()
    {
        return getChildExtension(IceUdpTransportPacketExtension.class);
    }

    /**
     * Get a builder for Transport objects.
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * Builder for Transport objects.
     */
    public static final class Builder
    {
        private boolean useUniquePort;

        private IceUdpTransportPacketExtension iceUdpExtension;

        public void setIceUdpExtension(IceUdpTransportPacketExtension iceUdpExtension)
        {
            this.iceUdpExtension = iceUdpExtension;
        }

        public void setUseUniquePort(boolean useUniquePort)
        {
            this.useUniquePort = useUniquePort;
        }

        Builder()
        {
        }

        public Transport build()
        {
            return new Transport(this);
        }
    }
}