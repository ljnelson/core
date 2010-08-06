/**
 * JBoss, Home of Professional Open Source
 * Copyright 2009, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
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
package org.jboss.weld.environment.se.example.simple;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import org.jboss.weld.environment.se.events.ContainerInitialized;
import javax.inject.Inject;

/**
 * @author Peter Royle
 */
@ApplicationScoped
public class HelloWorld
{

    @Inject
    CommandLineArgsValidator argsValidator;

    public HelloWorld()
    {
    }

    /**
     * Prints a hello message using the first name.
     * @param firstName The first name.
     */
    public void printHello( @Observes ContainerInitialized init )
    {
        if (!argsValidator.hasErrors())
        {
            System.out.println( "Hello " + argsValidator.getValidParameters().get( 0 ) );
        } else
        {
           for (String error : argsValidator.getErrors()) {
              System.out.println( error );
           }
        }
    }

}
