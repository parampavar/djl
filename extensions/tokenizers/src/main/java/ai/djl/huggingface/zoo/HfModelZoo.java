/*
 * Copyright 2022 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance
 * with the License. A copy of the License is located at
 *
 * http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package ai.djl.huggingface.zoo;

import ai.djl.Application;
import ai.djl.Application.CV;
import ai.djl.Application.NLP;
import ai.djl.engine.Engine;
import ai.djl.repository.RemoteRepository;
import ai.djl.repository.Repository;
import ai.djl.repository.Version;
import ai.djl.repository.VersionRange;
import ai.djl.repository.zoo.ModelLoader;
import ai.djl.repository.zoo.ModelZoo;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/** HfModelZoo is a repository that contains HuggingFace models. */
public class HfModelZoo extends ModelZoo {

    private static final Repository REPOSITORY = new RemoteRepository("Huggingface", DJL_REPO_URL);
    private static final String GROUP_ID = "ai.djl.huggingface.pytorch";

    private volatile boolean initialized; // NOPMD

    HfModelZoo() {}

    /** {@inheritDoc} */
    @Override
    public String getGroupId() {
        return GROUP_ID;
    }

    /** {@inheritDoc} */
    @Override
    public Set<String> getSupportedEngines() {
        return Collections.singleton("PyTorch");
    }

    /** {@inheritDoc} */
    @Override
    public Collection<ModelLoader> getModelLoaders() {
        init();
        return super.getModelLoaders();
    }

    /** {@inheritDoc} */
    @Override
    public ModelLoader getModelLoader(String name) {
        init();
        return super.getModelLoader(name);
    }

    private void init() {
        if (!initialized) {
            synchronized (HfModelZoo.class) {
                if (!initialized) {
                    Version version = new Version(Engine.getDjlVersion());
                    addModels(CV.ZERO_SHOT_IMAGE_CLASSIFICATION, version);
                    addModels(CV.ZERO_SHOT_OBJECT_DETECTION, version);
                    addModels(NLP.FILL_MASK, version);
                    addModels(NLP.QUESTION_ANSWER, version);
                    addModels(NLP.TEXT_CLASSIFICATION, version);
                    addModels(NLP.TEXT_EMBEDDING, version);
                    addModels(NLP.TOKEN_CLASSIFICATION, version);
                    addModels(NLP.ZERO_SHOT_CLASSIFICATION, version);
                    initialized = true;
                }
            }
        }
    }

    private void addModels(Application app, Version version) {
        Map<String, Map<String, Object>> map = listModels(REPOSITORY, app);
        for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
            Map<String, Object> model = entry.getValue();
            if ("failed".equals(model.get("result"))) {
                continue;
            }
            String requires = (String) model.get("requires");
            if (requires != null) {
                // the model requires specific DJL version
                VersionRange range = VersionRange.parse(requires);
                if (!range.contains(version)) {
                    continue;
                }
            }
            String artifactId = entry.getKey();
            addModel(REPOSITORY.model(app, GROUP_ID, artifactId, "0.0.1"));
        }
    }
}
