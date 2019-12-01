/*
 * Copyright (c) 2018, 2019 Oracle and/or its affiliates. All rights reserved.
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

package org.integrational.helidon.hellomp.apiimpl;

import org.integrational.helidon.hellomp.api.GreetResource;
import org.integrational.helidon.hellomp.svc.GreetingProvider;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import java.util.Collections;

@RequestScoped
public class GreetResourceImpl implements GreetResource {

    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

    private final GreetingProvider greetingProvider;

    /**
     * Using constructor injection to get a configuration property.
     * By default this gets the value from META-INF/microprofile-config
     *
     * @param greetingConfig the configured greeting message
     */
    @Inject
    public GreetResourceImpl(GreetingProvider greetingConfig) {
        this.greetingProvider = greetingConfig;
    }

    /**
     * Return a wordly greeting message.
     *
     * @return {@link JsonObject}
     */
    @Override
    public JsonObject getDefaultMessage() {
        return createResponse("World");
    }

    /**
     * Return a greeting message using the name that was provided.
     *
     * @param name the name to greet
     * @return {@link JsonObject}
     */
    @Override
    public JsonObject getMessage(String name) {
        return createResponse(name);
    }

    /**
     * Set the greeting to use in future messages.
     *
     * @param jsonObject JSON containing the new greeting
     * @return {@link Response}
     */
    @Override
    public Response updateGreeting(JsonObject jsonObject) {
        if (!jsonObject.containsKey("greeting")) {
            final var entity = JSON.createObjectBuilder()
                    .add("error", "No greeting provided")
                    .build();
            return Response.status(Response.Status.BAD_REQUEST).entity(entity).build();
        }

        final var newGreeting = jsonObject.getString("greeting");
        greetingProvider.setMessage(newGreeting);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    private JsonObject createResponse(String who) {
        final var msg = String.format("%s %s!", greetingProvider.getMessage(), who);
        return JSON.createObjectBuilder()
                .add("message", msg)
                .build();
    }
}
