/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.camel.component.soroushbot.component;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.component.soroushbot.models.ConnectionType;
import org.apache.camel.component.soroushbot.models.MessageModel;
import org.apache.camel.component.soroushbot.support.SoroushBotTestSupport;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ConsumerAutoDownloadFile extends SoroushBotTestSupport {
    @Override
    public RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("soroush://" + ConnectionType.getMessage + "?authorizationToken=4 File&autoDownload=true")
                        .to("mock:soroush");
            }
        };
    }

    @Test
    public void checkIfAutoDownloadFiles() throws InterruptedException {
        MockEndpoint mockEndpoint = getMockEndpoint("mock:soroush");
        mockEndpoint.setExpectedCount(4);
        mockEndpoint.assertIsSatisfied();
        List<Exchange> exchanges = mockEndpoint.getExchanges();
        Assert.assertEquals(exchanges.size(), 4);
        exchanges.forEach(exchange -> {
            MessageModel body = exchange.getIn().getBody(MessageModel.class);
            Assert.assertTrue("if fileUrl is not null file may not be null and visa versa", body.getFile() == null ^ body.getFileUrl() != null);
            Assert.assertTrue("if and only if thumbnail url is null thumbnail may be null", body.getThumbnail() == null ^ body.getThumbnailUrl() != null);
        });

    }
}
