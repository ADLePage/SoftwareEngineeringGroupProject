/*Copy this entire thing! Removing the surrounding comments
package com.example.baads;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
//Change FragmentSecondBinding to your page. It will follow the Pattern of FragmentNAMEOFYOURPAGEBinding
import com.example.baads.databinding.FragmentSecondBinding;
public class CopyFromThisToMakeNewPage extends Fragment {
    //Change FragmentSecondBinding to what you changed above to.
    private FragmentSecondBinding binding;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        //Change FragmentSecondBinding to what you changed above to.
        binding = FragmentSecondBinding.inflate(inflater, container, false);
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
*/