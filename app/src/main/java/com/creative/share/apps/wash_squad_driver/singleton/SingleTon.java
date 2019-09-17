package com.creative.share.apps.wash_squad_driver.singleton;

import android.util.Log;

import com.creative.share.apps.wash_squad_driver.models.ItemToUpload;

import java.util.ArrayList;
import java.util.List;

public class SingleTon {

    private static SingleTon instance = null;
    private static List<ItemToUpload> itemToUploadList ;

    private SingleTon() {
    }

    public static synchronized SingleTon newInstance()
    {
        if (instance==null)
        {
            instance = new SingleTon();
            itemToUploadList= new ArrayList<>();
        }

        return instance;
    }

    public void addItem(ItemToUpload itemToUpload)
    {
        this.itemToUploadList.add(itemToUpload);
        Log.e("item","added");
    }

    public void removeItem(int pos)
    {
        this.itemToUploadList.remove(pos);
    }

    public List<ItemToUpload> getData()
    {
        return this.itemToUploadList;
    }

    public int getItemsCount()
    {
        return this.itemToUploadList.size();
    }

    public void clear()
    {
        this.itemToUploadList.clear();
        instance = null;

    }
}
