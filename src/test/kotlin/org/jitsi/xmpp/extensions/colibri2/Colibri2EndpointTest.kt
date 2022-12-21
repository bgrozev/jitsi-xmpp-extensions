/*
 * Copyright @ 2022 - present 8x8, Inc.
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
package org.jitsi.xmpp.extensions.colibri2

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import org.jivesoftware.smack.parsing.SmackParsingException
import org.jivesoftware.smack.util.PacketParserUtils.getParserFor

class Colibri2EndpointTest : ShouldSpec() {
    val provider = Colibri2Endpoint.Provider()

    init {
        IqProviderUtils.registerProviders()
        context("Parsing") {
            context("With multiple transports") {
                val ep = provider.parse(
                    getParserFor(
                        """
                        <endpoint xmlns='jitsi:colibri2' id='bd9b6765' stats-id='Jayme-Clv'>
                            <transport ice-controlling='true'/>
                            <transport id='second-transport' use-unique-port='true'/>
                        </endpoint>
                        """
                    )
                )
                ep.transports.size shouldBe 2
                ep.transports.map { it.id } shouldBe setOf(Transport.ID_DEFAULT, "second-transport")
            }
            context("With multiple transports with the same ID") {
                shouldThrow<SmackParsingException> {
                    provider.parse(
                        getParserFor(
                            """
                            <endpoint xmlns='jitsi:colibri2' id='bd9b6765' stats-id='Jayme-Clv'>
                                <transport ice-controlling='true'/>
                                <transport id='second-transport' use-unique-port='true'/>
                                <transport id='second-transport' use-unique-port='true'/>
                            </endpoint>
                            """
                        )
                    )
                }
            }
        }
    }
}
