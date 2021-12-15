/*
 * Copyright @ 2021 - present 8x8, Inc.
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
package org.jitsi.xmpp.extensions.colibri2;

import org.jetbrains.annotations.*;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.*;

public class ConferenceModifyIQ
    extends AbstractConferenceModificationIQ<ConferenceModifyIQ>
{
    /**
     * The XML element name of the Jitsi Videobridge <tt>conference-modify</tt> IQ.
     */
    public static final String ELEMENT = "conference-modify";

    /**
     * The XML name of the attribute which controls whether rtcstats reporting should be enabled.
     */
    public static final String RTCSTATS_ENABLED_ATTR_NAME = "rtcstats-enabled";

    /**
     * The XML name of the attribute which controls whether callstats reporting should be enabled.
     */
    public static final String CALLSTATS_ENABLED_ATTR_NAME = "callstats-enabled";

    /**
     * The XML name of the <tt>name</tt> attribute of the Jitsi Videobridge
     * <tt>conference</tt> IQ which represents the value of the <tt>name</tt>
     * property of <tt>ConferenceModifyIQ</tt> if available.
     */
    public static final String NAME_ATTR_NAME = "name";

    /**
     * The XML name of the <tt>meeting-id</tt> attribute of the Jitsi Videobridge
     * <tt>conference</tt> IQ which represents the value of the <tt>name</tt>
     * property of <tt>ConferenceModifyIQ</tt> if available.
     */
    public static final String MEETING_ID_ATTR_NAME = "meeting-id";

    /**
     * The id of the conference
     */
    private final String meetingId;

    /**
     * The name of the conference
     */
    private final String name;

    /**
     * Whether rtcstats reporting should be enabled for the conference. This defaults to "true" if the XML attribute is
     * missing.
     */
    private final boolean rtcstatsEnabled;

    /**
     * Whether callstats reporting should be enabled for the conference. This defaults to "true" if the XML attribute is
     * missing.
     */
    private final boolean callstatsEnabled;

    /** Initializes a new <tt>ConferenceModifyIQ</tt> instance. */
    private ConferenceModifyIQ(Builder b)
    {
        super(b, ELEMENT);

        rtcstatsEnabled = b.rtcstatsEnabled;
        callstatsEnabled = b.callstatsEnabled;

        if (b.meetingId == null)
        {
            throw new IllegalArgumentException("meeting-id must be set for " + ELEMENT + " IQ");
        }
        meetingId = b.meetingId;

        if (b.conferenceName == null)
        {
            throw new IllegalArgumentException("name must be set for " + ELEMENT + " IQ");
        }
        name = b.conferenceName;
    }

    @Override
    protected IQChildElementXmlStringBuilder getIQChildElementBuilder(IQChildElementXmlStringBuilder xml)
    {
        xml.attribute(MEETING_ID_ATTR_NAME, meetingId);
        xml.attribute(NAME_ATTR_NAME, name);

        if (!rtcstatsEnabled)
        {
            xml.attribute(RTCSTATS_ENABLED_ATTR_NAME, false);
        }
        if (!callstatsEnabled)
        {
            xml.attribute(CALLSTATS_ENABLED_ATTR_NAME, false);
        }

        /* All our elements are extensions, so we just need to return empty here. */
        xml.setEmptyElement();

        return xml;
    }

    /**
     * Get the name of the conference.
     */
    public @NotNull String getConferenceName()
    {
        return name;
    }

    /**
     * Get the ID of the conference.
     */
    public @NotNull String getMeetingId()
    {
        return meetingId;
    }

    public boolean isRtcstatsEnabled()
    {
        return rtcstatsEnabled;
    }

    public boolean isCallstatsEnabled()
    {
        return callstatsEnabled;
    }

    @Contract("_ -> new")
    public static @NotNull Builder builder(XMPPConnection connection)
    {
        return new Builder(connection);
    }

    @Contract("_ -> new")
    public static @NotNull Builder builder(IqData iqData)
    {
        return new Builder(iqData);
    }

    @Contract("_ -> new")
    public static @NotNull Builder builder(String stanzaId)
    {
        return new Builder(stanzaId);
    }

    public static final class Builder
        extends AbstractConferenceModificationIQ.Builder<ConferenceModifyIQ>
    {
        private boolean rtcstatsEnabled = true;
        private boolean callstatsEnabled = true;
        private String conferenceName;
        private String meetingId;

        private Builder(IqData iqCommon) {
            super(iqCommon);
        }

        private Builder(XMPPConnection connection) {
            super(connection);
        }

        private Builder(String stanzaId) {
            super(stanzaId);
        }

        public Builder setRtcstatsEnabled(boolean rtcstatsEnabled)
        {
            this.rtcstatsEnabled = rtcstatsEnabled;
            return this;
        }

        public Builder setCallstatsEnabled(boolean callstatsEnabled)
        {
            this.callstatsEnabled = callstatsEnabled;
            return this;
        }

        public Builder setConferenceName(String name)
        {
            conferenceName = name;

            return this;
        }

        public Builder setMeetingId(String id)
        {
            meetingId = id;

            return this;
        }

        @Override
        @Contract(" -> new")
        public @NotNull ConferenceModifyIQ build()
        {
            return new ConferenceModifyIQ(this);
        }
    }
}
