/*******************************************************************************
 * Copyright (c) 2013-2014 LAAS-CNRS (www.laas.fr)
 * 7 Colonel Roche 31077 Toulouse - France
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Thierry Monteil (Project co-founder) - Management and initial specification,
 *         conception and documentation.
 *     Mahdi Ben Alaya (Project co-founder) - Management and initial specification,
 *         conception, implementation, test and documentation.
 *     Christophe Chassot - Management and initial specification.
 *     Khalil Drira - Management and initial specification.
 *     Yassine Banouar - Initial specification, conception, implementation, test
 *         and documentation.
 ******************************************************************************/
package org.eclipse.om2m.core.dao;

import java.util.Date;

import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.Containers;
import org.eclipse.om2m.commons.resource.ContentInstances;
import org.eclipse.om2m.commons.resource.Subscriptions;
import org.eclipse.om2m.commons.utils.DateConverter;

import com.db4o.ObjectSet;
import com.db4o.query.Query;

/**
 * Implements CRUD Methods for {@link Container} resource persistence.
 *
 * @author <ul>
 *         <li>Yessine Feki < yfeki@laas.fr > < yessine.feki@ieee.org ></li>
 *         <li>Mahdi Ben Alaya < ben.alaya@laas.fr > < benalaya.mahdi@gmail.com ></li>
 *         <li>Yassine Banouar < ybanouar@laas.fr > < yassine.banouar@gmail.com ></li>
 *         </ul>
 */
public class ContainerDAO extends DAO<Container> {

    /**
     * Creates an {@link Container} resource in the DataBase and validates the transaction
     * @param resource - The {@link Container} resource to create
     */
    public void create(Container resource) {
        // Store the created resource
        DB.store(resource);
        // ContentInstances
        ContentInstances contentInstances = new ContentInstances();
        contentInstances.setUri(resource.getContentInstancesReference());
        contentInstances.setCurrentNrOfInstances(0L);
        contentInstances.setCurrentByteSize(0L);
        contentInstances.setCreationTime(resource.getCreationTime());
        contentInstances.setLastModifiedTime(resource.getLastModifiedTime());
        DAOFactory.getContentInstancesDAO().create(contentInstances);
        // Subscriptions
        Subscriptions subscriptions = new Subscriptions();
        subscriptions.setUri(resource.getSubscriptionsReference());
        DAOFactory.getSubscriptionsDAO().create(subscriptions);
        // Create the query based on the uri constraint
        Query query = DB.query();
        query.constrain(Containers.class);
        query.descend("uri").constrain(resource.getUri().split("/"+resource.getId())[0]);
        // Store all the founded resources
        ObjectSet<Containers> result = query.execute();
        
        // Update the lastModifiedTime attribute of the parent
        Containers containers = result.get(0);
        // Update the lastModifiedTime attribute of the parent
        containers.setLastModifiedTime(DateConverter.toXMLGregorianCalendar(new Date()).toString());
        DB.store(containers);
        // Validate the current transaction
        commit();
    }

    /**
     * Retrieves the {@link Container} resource from the Database based on its uri
     * @param uri - uri of the {@link Container} resource to retrieve
     * @return The requested {@link Container} resource otherwise null
     */
    public Container find(String uri){
        // Create the query based on the uri constraint
        Query query = DB.query();
        query.constrain(Container.class);
        query.descend("uri").constrain(uri);
        // Store all the founded resources
        ObjectSet<Container> result=query.execute();
        // Retrieve the first element corresponding to the researched resource if result is not empty
        if (!result.isEmpty()) {
            return result.get(0);
        }
        // Return null if the resource is not found
        return null;
    }

    /**
     * Retrieves the {@link Container} resource from the Database based on the uri
     * @param uri - uri of the {@link Container} resource
     * @return The requested {@link Container} resource otherwise null
     */
    public Container lazyFind(String uri) {
        return find(uri);
    }

    /**
     * Updates an existing {@link Container} resource in the DataBase
     * @param resource - The {@link Container} the updated resource
     */
    public void update(Container resource) {
        // Store the updated resource
        DB.store(resource);
        // Create the query based on the uri constraint
        Query query = DB.query();
        query.constrain(Containers.class);
        query.descend("uri").constrain(resource.getUri().split("/"+resource.getId())[0]);
        // Store all the founded resources
        ObjectSet<Containers> result = query.execute();
        
        // Update the lastModifiedTime attribute of the parent
        Containers containers = result.get(0);
        // Update the lastModifiedTime attribute of the parent
        containers.setLastModifiedTime(DateConverter.toXMLGregorianCalendar(new Date()).toString());
        DB.store(containers);
        // Validate the current transaction
        commit();
    }

    /**
     * Deletes the {@link Container} resource from the DataBase and validates the transaction
     * @Param the {@link Container} resource to delete
     */
    public void delete(Container resource) {
        // Delete the resource
        lazyDelete(resource);
        // Validate the current transaction
        commit();
    }

    /**
     * Deletes the {@link Container} resource from the DataBase without validating the transaction
     * @param resource - The {@link Container} resource to delete
     */
    public void lazyDelete(Container resource){
        //delete subscriptions
        DAOFactory.getSubscriptionsDAO().lazyDelete(DAOFactory.getSubscriptionsDAO().lazyFind(resource.getSubscriptionsReference()));
        //delete contentInstances
        DAOFactory.getContentInstancesDAO().lazyDelete(DAOFactory.getContentInstancesDAO().lazyFind(resource.getContentInstancesReference()));
        // Delete the resource
        DB.delete(resource);

        // Create the query based on the uri constraint
        Query query = DB.query();
        query.constrain(Containers.class);
        query.descend("uri").constrain(resource.getUri().split("/"+resource.getId())[0]);
        // Store all the founded resources
        ObjectSet<Containers> result = query.execute();
        
        // Update the lastModifiedTime attribute of the parent
        Containers containers = result.get(0);
        // Update the lastModifiedTime attribute of the parent
        containers.setLastModifiedTime(DateConverter.toXMLGregorianCalendar(new Date()).toString());
        DB.store(containers);
    }
}
