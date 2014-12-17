// ============================================================================
//
// Copyright (C) 2006-2014 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.core.model.utils;

import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.properties.Item;
import org.talend.core.model.properties.Property;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.repository.model.IRepositoryNode;

/**
 * created by wchen on 2013-5-20 Detailled comment
 * 
 */
public abstract class AbstractDragAndDropServiceHandler implements IDragAndDropServiceHandler {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.core.model.utils.IDragAndDropServiceHandler#getComponentValue(org.talend.core.model.metadata.builder
     * .connection.Connection, java.lang.String, org.talend.core.model.metadata.IMetadataTable)
     */
    @Override
    public Object getComponentValue(Connection connection, String value, IMetadataTable table) {
        return getComponentValue(connection, value, table, null);
    }

    public IComponentName getCorrespondingComponentName(IRepositoryNode repositoryNode, ERepositoryObjectType type) {
        Item item = getItemFromRepositoryNode(repositoryNode);
        return getCorrespondingComponentName(item, type);
    }

    protected Item getItemFromRepositoryNode(IRepositoryNode repositoryNode) {
        Item item = null;
        if (repositoryNode == null) {
            return item;
        }
        IRepositoryViewObject viewObject = repositoryNode.getObject();
        if (viewObject == null) {
            return item;
        }
        Property property = viewObject.getProperty();
        if (property == null) {
            return item;
        }
        item = property.getItem();
        return item;
    }
}
