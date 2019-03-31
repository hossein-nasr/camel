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
import org.apache.camel.component.soroushbot.models.ConnectionType;
import org.apache.camel.component.soroushbot.models.MessageModel;
import org.apache.camel.support.DefaultProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * this producer is responsible for uri of type {@link ConnectionType#downloadFile}
 * e.g. "soroush:downloadFile/[token]"
 * if you pass a message to this endpoint, it tries to download the resource
 * ({@link MessageModel#fileUrl} and {@link MessageModel#thumbnailUrl})
 * if provided and store them in
 * {@link MessageModel#file} or {@link MessageModel#thumbnail}.
 */
public class SoroushBotDownloadFileProducer extends DefaultProducer {
    static Logger log = LoggerFactory.getLogger(SoroushBotDownloadFileProducer.class);
    SoroushBotEndpoint endpoint;

    public SoroushBotDownloadFileProducer(SoroushBotEndpoint endpoint) {
        super(endpoint);
        this.endpoint = endpoint;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        MessageModel body = exchange.getIn().getBody(MessageModel.class);
        endpoint.handleDownloadFiles(body);
    }
}
