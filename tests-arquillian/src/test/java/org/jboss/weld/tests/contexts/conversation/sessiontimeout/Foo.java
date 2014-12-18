/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.weld.tests.contexts.conversation.sessiontimeout;

import java.io.Serializable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ConversationScoped;

import org.jboss.weld.test.util.ActionSequence;

@SuppressWarnings("serial")
@ConversationScoped
public class Foo implements Serializable {

    volatile CountDownLatch doneSignal;

    public void pong() {
    }

    public void ping() {
        doneSignal = new CountDownLatch(1);
        ActionSequence.addAction(Foo.class.getSimpleName()+"pingStart");
        try {
            doneSignal.await(3000l, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ActionSequence.addAction(Foo.class.getSimpleName()+"pingEnd");
    }

    @PostConstruct
    public void init() {
        ActionSequence.addAction(Foo.class.getSimpleName()+"init");
    }

    @PreDestroy
    public void destroy() {
        ActionSequence.addAction(Foo.class.getSimpleName()+"destroy");
        doneSignal.countDown();
    }

}