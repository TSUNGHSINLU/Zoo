package com.ivan.animal.presenter;

import android.support.v17.leanback.widget.Presenter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ivan.animal.R;

/**
 * Default Presenter
 * Created by Ivan on 2017/1/15.
 *
 * @author IvanLu
 */
public class DefaultPresenter extends Presenter {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.cardview_default_item, parent, false);
        return new DefaultViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        DefaultViewHolder defaultViewHolder = (DefaultViewHolder) viewHolder;
        defaultViewHolder.defaultMessage.setText((String)item);
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
    }

    private class DefaultViewHolder extends ViewHolder {

        public TextView defaultMessage;

        DefaultViewHolder(View view) {
            super(view);
            defaultMessage = view.findViewById(R.id.default_message);
        }
    }
}
