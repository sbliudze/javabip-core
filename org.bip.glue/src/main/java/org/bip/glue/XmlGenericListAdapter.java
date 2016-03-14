package org.bip.glue;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.adapters.XmlAdapter;

class XmlGenericListAdapter<T extends List<PortBaseImpl>> extends XmlAdapter<ListType<T>, List<T>> {

    @Override
    public List<T> unmarshal(ListType<T> v) throws Exception {
        List<T> list = new ArrayList<T>();

        for (ListElementType<T> listElementType : v.getList()) {
            list.add(listElementType.getValue());
        }
        return list;
    }

    @Override
    public ListType<T> marshal(List<T> v) throws Exception {
        ListType<T> listType = new ListType<T>();

        for (T entry : v) {
            ListElementType<T> listElementType = new ListElementType<T>();
            listElementType.setValue(entry);
            listType.getList().add(listElementType);
        }
        return listType;
    }
}
