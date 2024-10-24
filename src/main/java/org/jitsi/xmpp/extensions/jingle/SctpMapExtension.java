/*
 * Copyright @ 2018 - present 8x8, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jitsi.xmpp.extensions.jingle;

import org.jivesoftware.smack.packet.*;
import org.jivesoftware.smack.util.*;

/**
 * SctpMap extension in transport packet extension.
 * Defined by XEP-0343: Signaling WebRTC datachannels in Jingle.
 *
 * @author lishunyang
 *
 */
public class SctpMapExtension
    implements ExtensionElement
{
    /**
     * The name of the "sctpmap" element.
     */
    public static final String ELEMENT = "sctpmap";

    /**
     * The namespace for the "sctpmap" element.
     */
    public static final String NAMESPACE =
        "urn:xmpp:jingle:transports:dtls-sctp:1";

    /**
     * Port number of "sctpmap" element.
     */
    public static final String PORT_ATTR_NAME = "number";

    /**
     * Protocol name of "sctpmap" element.
     */
    public static final String PROTOCOL_ATTR_NAME = "protocol";

    /**
     * Number of streams of "sctpmap" element.
     */
    public static final String STREAMS_ATTR_NAME = "streams";

    /**
     * Value of "port".
     */
    private int port = -1;

    /**
     * Value of "protocol".
     * @See SctpMapExtension.Protocol
     */
    private String protocol = "";

    /**
     * Number of "streams".
     */
    private int streams = -1;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getElementName()
    {
        return ELEMENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNamespace()
    {
        return NAMESPACE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toXML(XmlEnvironment enclosingNamespace)
    {
        XmlStringBuilder xml = new XmlStringBuilder();

        xml.halfOpenElement(getElementName());
        xml.xmlnsAttribute(getNamespace());

        xml.optAttribute(PORT_ATTR_NAME, String.valueOf(port));
        xml.optAttribute(PROTOCOL_ATTR_NAME, protocol);
        xml.optAttribute(STREAMS_ATTR_NAME, String.valueOf(streams));

        xml.closeEmptyElement();

        return xml.toString();
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public int getPort()
    {
        return port;
    }

    public void setProtocol(String protocol)
    {
        this.protocol = protocol;
    }

    public void setProtocol(Protocol protocol)
    {
        this.protocol = protocol.toString();
    }

    public String getProtocol()
    {
        return protocol;
    }

    public void setStreams(int streams)
    {
        this.streams = streams;
    }

    public int getStreams()
    {
        return streams;
    }

    /**
     * Protocol enumeration of <tt>SctpMapExtension</tt>. Currently it only
     * contains WEBRTC_CHANNEL.
     *
     * @author lishunyang
     *
     */
    public static enum Protocol
    {
        WEBRTC_CHANNEL("webrtc-datachannel");

        private String name;

        private Protocol(String name)
        {
            this.name = name;
        }

        @Override
        public String toString()
        {
            return name;
        }
    }
}
