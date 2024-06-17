/*
 * Copyright (c) 2023 WildFireChat. All rights reserved.
 */

package cn.wildfire.chat.kit.mesh;

import android.content.Intent;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.wildfire.chat.kit.R;
import cn.wildfire.chat.kit.contact.ContactViewModel;
import cn.wildfire.chat.kit.widget.ProgressFragment;
import cn.wildfirechat.model.DomainInfo;

public class ExternalOrganizationListFragment extends ProgressFragment implements ExternalOrganizationListAdapter.OnExternalOrganizationClickListener {
    private RecyclerView recyclerView;
    private ExternalOrganizationListAdapter adapter;

    private ContactViewModel contactViewModel;

    @Override
    protected int contentLayout() {
        return R.layout.external_organization_list_fragment;
    }

    @Override
    protected void afterViews(View view) {
        super.afterViews(view);
        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new ExternalOrganizationListAdapter(this);
        adapter.setOnExternalOrganizationClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        loadRemoteDomains();
    }


    @Override
    public void onExternalOrganizationClick(DomainInfo domainInfo) {
        Intent intent = new Intent(getContext(), ExternalOrganizationInfoActivity.class);
        intent.putExtra("domainInfo", domainInfo);
        startActivity(intent);
    }

    private void loadRemoteDomains() {

        this.contactViewModel.loadRemoteDomains()
            .observe(this, new Observer<List<DomainInfo>>() {
                @Override
                public void onChanged(List<DomainInfo> domainInfos) {
                    adapter.setDomainInfos(domainInfos);
                    showContent();
                }
            });
    }
}
