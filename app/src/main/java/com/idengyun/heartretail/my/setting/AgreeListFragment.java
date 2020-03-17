package com.idengyun.heartretail.my.setting;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.splashmodule.config.SpProtocol;
import com.idengyun.heartretail.HRActivity;
import com.idengyun.heartretail.HRSession;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.model.response.ProtocolsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户协议
 *
 * @author aLang
 */
public final class AgreeListFragment extends BaseFragment {

    private RecyclerView recycler_view;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_agreements;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViewById(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        requestAPI();
    }

    private void requestAPI() {
        HRSession.session_08(this, SpProtocol.getAllProtocolIDs(), new Observer<List<ProtocolsBean.Data>>() {
            @Override
            public void onChanged(@Nullable List<ProtocolsBean.Data> data) {
                updateUI(data);
            }
        });
    }

    @MainThread
    private void updateUI(List<ProtocolsBean.Data> dataList) {
        AgreeAdapter adapter = new AgreeAdapter();
        adapter.agreeList.addAll(dataList);
        recycler_view.setAdapter(adapter);
    }

    private void findViewById(View view) {
        recycler_view = view.findViewById(R.id.recycler_view);
    }

    private static class AgreeAdapter extends RecyclerView.Adapter<AgreeAdapter.AgreeHolder> {

        private LayoutInflater inflater;
        final List<ProtocolsBean.Data> agreeList = new ArrayList<>();

        @NonNull
        @Override
        public AgreeHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            if (inflater == null) inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.fragment_agreements_item, parent, false);
            return new AgreeHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull AgreeHolder holder, int position) {
            holder.updateUI(agreeList.get(position));
        }

        @Override
        public int getItemCount() {
            return agreeList.size();
        }

        private class AgreeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView tv_agree_title;
            private View v_agree_divider;

            public AgreeHolder(@NonNull View itemView) {
                super(itemView);
                findViewById(itemView);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                ProtocolsBean.Data data = agreeList.get(getAdapterPosition());
                extras.putInt("protocol_id", data.protocolId);
                extras.putString("protocol_name", data.protocolName);
                extras.putString("protocol_content", data.protocolContent);
                HRActivity.start(v.getContext(), extras, AgreeDetailFragment.class);
            }

            public void updateUI(ProtocolsBean.Data data) {
                tv_agree_title.setText(data.protocolName);
                v_agree_divider.setVisibility(getAdapterPosition() == getItemCount() - 1 ? View.INVISIBLE : View.VISIBLE);
            }

            private void findViewById(@NonNull View itemView) {
                tv_agree_title = itemView.findViewById(R.id.tv_agree_title);
                v_agree_divider = itemView.findViewById(R.id.v_agree_divider);
            }

        }
    }
}
