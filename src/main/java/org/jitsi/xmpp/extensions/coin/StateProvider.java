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
package org.jitsi.xmpp.extensions.coin;

import org.jivesoftware.smack.packet.*;
import org.jivesoftware.smack.parsing.*;
import org.jivesoftware.smack.provider.*;
import org.jivesoftware.smack.xml.*;

import java.io.*;

/**
 * Parser for StatePacketExtension.
 *
 * @author Sebastien Vincent
 */
public class StateProvider
    extends ExtensionElementProvider<StatePacketExtension>
{
    /**
     * Parses a state extension sub-packet and creates a {@link
     * StatePacketExtension} instance. At the beginning of the method
     * call, the xml parser will be positioned on the opening element of the
     * packet extension. As required by the smack API, at the end of the method
     * call, the parser will be positioned on the closing element of the packet
     * extension.
     *
     * @param parser an XML parser positioned at the opening
     * <tt>State</tt> element.
     *
     * @return a new {@link StatePacketExtension} instance.
     * @throws java.lang.Exception if an error occurs parsing the XML.
     */
    @Override
    public StatePacketExtension parse(XmlPullParser parser, int depth, XmlEnvironment xmlEnvironment)
        throws XmlPullParserException, IOException, SmackParsingException
    {
        boolean done = false;
        XmlPullParser.Event eventType;
        String elementName = null;

        StatePacketExtension ext
            = new StatePacketExtension();

        while (!done)
        {
            eventType = parser.next();
            elementName = parser.getName();

            if (eventType == XmlPullParser.Event.START_ELEMENT)
            {
                if (elementName.equals(StatePacketExtension.ELEMENT_ACTIVE))
                {
                    ext.setActive(
                            Boolean.parseBoolean(ParsingUtils.parseText(parser)) ? 1 : 0);
                }
                else if (elementName.equals(StatePacketExtension.ELEMENT_LOCKED))
                {
                    ext.setLocked(
                            Boolean.parseBoolean(ParsingUtils.parseText(parser)) ? 1 : 0);
                }
                if (elementName.equals(StatePacketExtension.ELEMENT_USER_COUNT))
                {
                    ext.setUserCount(Integer.parseInt(ParsingUtils.parseText(parser)));
                }
            }
            else if (eventType == XmlPullParser.Event.END_ELEMENT)
            {
                if (parser.getName().equals(
                        StatePacketExtension.ELEMENT))
                {
                    done = true;
                }
            }
        }

        return ext;
    }
}
