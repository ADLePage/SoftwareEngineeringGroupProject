package com.example.baads.youtubePlayer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.baads.databinding.ActivityYouTubeBinding;
import com.example.baads.databinding.ActivityYoutubePlayerReworkedBinding;
import com.example.baads.databinding.FragmentYoutubeplayerBinding;

public class youtubePlayerFragmentReworked extends Fragment {

    private ActivityYoutubePlayerReworkedBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = ActivityYoutubePlayerReworkedBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}