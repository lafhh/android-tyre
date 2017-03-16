package com.laf.baggoods.ui.basic.listview;

import android.util.SparseArray;
import android.view.View;

/**
 * Created by apple on 2017/3/14.
 */

public class ViewHolderUtils {
    public ViewHolder build(View container) {
        ViewHolder viewHolder = (ViewHolder) container.getTag();

        if (viewHolder == null) {
            viewHolder = new ViewHolder(container);
            container.setTag(viewHolder);
        }
        return viewHolder;
    }

    public class ViewHolder {
        private SparseArray<View> viewArray;
        private View container;

        public ViewHolder(View container) {
            this.container = container;
            viewArray = new SparseArray<>();
        }

        public <T extends View> T getViewById(int id) {
            View childView = viewArray.get(id);

            if (childView == null) {
                childView = container.findViewById(id);
                viewArray.put(id, childView);
            }
            return (T) childView;
        }

    }
}
