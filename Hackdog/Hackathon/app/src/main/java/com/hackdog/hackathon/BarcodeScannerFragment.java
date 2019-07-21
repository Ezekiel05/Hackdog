package com.hackdog.hackathon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import com.google.zxing.Result;
import com.hackdog.hackathon.databinding.FragmentBarcodeScannerBinding;
import com.hackdog.hackathon.viewmodels.BarcodeViewModel;
import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class BarcodeScannerFragment extends Fragment implements ZXingScannerView.ResultHandler {

    private ZXingScannerView scannerView;

    private FragmentBarcodeScannerBinding mBinding;
    private BarcodeViewModel barcodeViewModel;

    private View main;

    private LinearLayout cameraArea;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        barcodeViewModel = ViewModelProviders.of(getActivity()).get(BarcodeViewModel.class);

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_barcode_scanner, container, false);
        mBinding.setLifecycleOwner(getViewLifecycleOwner());
        mBinding.setBarcodeViewModel(barcodeViewModel);

        main = this.getActivity().findViewById(android.R.id.content);

        cameraArea = mBinding.cameraArea;

        scannerView = new ZXingScannerView(getActivity().getApplicationContext());
        scannerView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        cameraArea.addView(scannerView);
        scannerView.setResultHandler(this);
        scannerView.startCamera();
        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        Toast.makeText(getActivity(), result.getText(), Toast.LENGTH_SHORT).show();
        scannerView.resumeCameraPreview(this);
        barcodeViewModel.setBarcodeScanned(result.getText());
        scannerView.removeAllViews();
        scannerView.stopCamera();

        assert getFragmentManager() != null;
        getFragmentManager().popBackStack();
    }
}
