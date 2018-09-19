package com.example.user.myapp.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.myapp.R;
import com.example.user.myapp.api.ApiClient;
import com.example.user.myapp.db.Message;
import com.example.user.myapp.db.MessageDao;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import toothpick.Toothpick;

import static com.example.user.myapp.Constants.APP_SCOPE;

public class MainFragment extends Fragment {
    @Inject
    ApiClient apiClient;
    @Inject
    MessageDao messageDao;
    private Button btnSend;
    private EditText etUser;
    private EditText etMessage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toothpick.inject(this, Toothpick.openScope(APP_SCOPE));
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @SuppressLint("ShowToast")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSend = view.findViewById(R.id.btnSend);
        etUser = view.findViewById(R.id.etUser);
        etMessage = view.findViewById(R.id.etMessage);
        btnSend.setOnClickListener(v -> {
            String userKey = etUser.getText().toString();
            String messageToSend = etMessage.getText().toString();
            enableUi(false);
            Timber.d("message: %s/%s", etUser.getText().toString(), etMessage.getText().toString());

            apiClient.sendMessageResponseSingle(userKey, messageToSend)
                    .map(sendMessageResponse -> {
                        Message dbMessage = new Message();
                        dbMessage.messageText = messageToSend;
                        dbMessage.userKey = userKey;
                        dbMessage.request = sendMessageResponse.request;
                        dbMessage.status = sendMessageResponse.status;
                        return dbMessage;
                    })
                    .map(dbMessage -> messageDao.insertMessage(dbMessage))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            along -> {
                                enableUi(true);
                                Toast.makeText(getContext(), R.string.successMessage, Toast.LENGTH_LONG);
                                etMessage.setText(null);
                            },
                            error -> {
                                enableUi(true);
                                Toast.makeText(getContext(), R.string.failMessage, Toast.LENGTH_LONG);
                            }
                    );
        });
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_items, menu);
    }

    private void enableUi(boolean enableUi) {
        btnSend.setEnabled(enableUi);
        etUser.setEnabled(enableUi);
        etMessage.setEnabled(enableUi);
    }
}
