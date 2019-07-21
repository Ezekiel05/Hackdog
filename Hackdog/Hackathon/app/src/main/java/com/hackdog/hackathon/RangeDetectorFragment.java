package com.hackdog.hackathon;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.hackdog.hackathon.constants.Strings;
import com.hackdog.hackathon.databinding.FragmentRangeDetectorBinding;
import com.hackdog.hackathon.viewmodels.LocationViewModel;
import com.hackdog.hackathon.viewmodels.ShoppingListViewModel;
import org.altbeacon.beacon.*;

import java.util.ArrayList;
import java.util.Collection;

public class RangeDetectorFragment extends Fragment implements BeaconConsumer, RangeNotifier, View.OnClickListener {

    private static final String TAG = "RangingActivity";
    private BeaconManager mBeaconManager;

    private FragmentRangeDetectorBinding mBinding;
    private LocationViewModel locationViewModel;
    private ShoppingListViewModel shopVm;

    private String section = "";

    private IsleProductsFragment isleProductsFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        locationViewModel = ViewModelProviders.of(getActivity()).get(LocationViewModel.class);
        shopVm= ViewModelProviders.of(getActivity()).get(ShoppingListViewModel.class);

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_range_detector, container, false);
        mBinding.setLifecycleOwner(getViewLifecycleOwner());
        mBinding.setLocationViewmodel(locationViewModel);

        mBeaconManager = BeaconManager.getInstanceForApplication(getApplicationContext());
        // In this example, we will use Eddystone protocol, so we have to define it here
        mBeaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT));
        // Binds this activity to the BeaconService
        mBeaconManager.bind(this);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        requestPermissions(new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, 1234);

        if(!section.equals("")){
            locationViewModel.setNearestSection(section);
        }

        locationViewModel.getNearestSection().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
//                mBinding.beaconRange.setText(s);
                if(s.equals("Meat Section")){
                    mBinding.meatMapPin.setVisibility(View.VISIBLE);
                    mBinding.beveragesMapPin.setVisibility(View.INVISIBLE);
                }
                else if(s.equals("Beverages Section")){
                    mBinding.meatMapPin.setVisibility(View.INVISIBLE);
                    mBinding.beveragesMapPin.setVisibility(View.VISIBLE);
                }

                Log.d("ahgd", s);
            }
        });

//        btnAlcohol = findViewById(R.id.Alcoholbtn)
//        btnCarbonated = findViewById(R.id.Carbonatedbtn)
//        btnGrilled.setOnClickListener {
//            mBinding.cartRecyclerView.adapter =
//                    CartRecyclerAdapter(it) { img: Products, s: String -> productClicked(img, s) }
//        }

//        mBinding.Teabtn.setOnClickListener (new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
////                if(!isleProductsFragment.isAdded()){
//////                    shopVm.getProductsByIsleID(1);
////                    ft.replace(R.id.productDetailsLayout, isleProductsFragment);
////                    ft.commit();
//                }
//            }
//        });




        mBinding.GroceryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = RangeDetectorFragmentDirections.actionRangeDetectorFragmentToShoppingListFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        mBinding.NutsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = RangeDetectorFragmentDirections.actionRangeDetectorFragmentToShoppingListFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });
    }

    @Override
    public void onBeaconServiceConnect() {
        // Encapsulates a beacon identifier of arbitrary byte length
        ArrayList<Identifier> identifiers = new ArrayList<>();

        // Set null to indicate that we want to match beacons with any value
        identifiers.add(null);
        // Represents a criteria of fields used to match beacon
        Region region = new Region(("AllBeaconsRegion"), identifiers);
        try {
            // Tells the BeaconService to start looking for beacons that match the passed Region object
            mBeaconManager.startRangingBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        // Specifies a class that should be called each time the BeaconService gets ranging data, once per second by default
        mBeaconManager.addRangeNotifier(this);
    }

    @Override
    public Context getApplicationContext() {
        return getActivity().getApplicationContext();
    }

    @Override
    public void unbindService(ServiceConnection serviceConnection) {
        getActivity().unbindService(serviceConnection);
    }

    @Override
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
        return getActivity().bindService(intent, serviceConnection, i);
    }
    @Override
    public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
        if (beacons.size() > 0) {
            ArrayList<Beacon> beaconArrayList = (ArrayList<Beacon>) beacons;

            StringBuilder uid = new StringBuilder();
            StringBuilder distance = new StringBuilder();
            StringBuilder add = new StringBuilder();

            Log.i(TAG, add.toString());

            String nearestBeacon = "";
            double minValue = beaconArrayList.get(0).getRunningAverageRssi();

            for (int i = 0; i < beacons.size(); i++) {
                add.append("\n").append(beaconArrayList.get(i).getId1());
                distance.append("\n").append(beaconArrayList.get(i).getRunningAverageRssi());

                if(beaconArrayList.get(i).getRssi() > minValue){
                    minValue = beaconArrayList.get(i).getRunningAverageRssi();
                    nearestBeacon = beaconArrayList.get(i).getId1().toString();
                }
                Log.i(TAG, add.toString());
            }

//            mBinding.beaconSizeTxt.setText("nearest beacon " + nearestBeacon);
            if(nearestBeacon.equals(Strings.meatSection)){
                section = "Meat Section";
            }
            else if(nearestBeacon.equals(Strings.beveragesSection)){
                section = "Beverages Section";
            }

            locationViewModel.setNearestSection(section);

        }
        else
        {
        }


    }

    @Override
    public void onClick(View view) {
        Toast.makeText(getActivity().getApplicationContext(), "Button Clikced", Toast.LENGTH_SHORT).show();

    }
}
