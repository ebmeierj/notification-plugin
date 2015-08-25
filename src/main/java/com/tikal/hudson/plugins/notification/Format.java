/**
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
package com.tikal.hudson.plugins.notification;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import com.tikal.hudson.plugins.notification.model.JobState;

import java.io.IOException;

public enum Format {
    XML {
        private final XStream xstream = new XStream();

        @Override
        protected byte[] serialize(JobState jobState) throws IOException {
            xstream.processAnnotations(JobState.class);
            return xstream.toXML(jobState).getBytes( "UTF-8" );
        }
    },
    JSON {
        private final Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

        @Override
        protected byte[] serialize(JobState jobState) throws IOException {
            JsonElement jsonElement = gson.toJsonTree(jobState);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            jsonObject.addProperty("auth_token", "YOUR_AUTH_TOKEN");
            jsonObject.addProperty("value", 10);
            return gson.toJson(jsonObject).getBytes( "UTF-8" );
        }
    };

    protected abstract byte[] serialize(JobState jobState) throws IOException;
}
