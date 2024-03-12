// justin chipman n01598472
package justin.chipman.n01598472;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.Manifest;

public class SettingsFragment extends Fragment {

    ImageView imageView;
    Button button;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private ActivityResultLauncher<Intent> galleryLauncher;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                Toast.makeText(requireContext(), "Permission allowed", Toast.LENGTH_SHORT).show();
                openGallery();
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                } else {
                    // Permission denied twice
                    Toast.makeText(requireContext(), "Permission denied. Manually allow permission", Toast.LENGTH_SHORT).show();
                    redirectToAppInfo();
                }
            }
        });

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                Uri selectedImage = result.getData().getData();
                // Do something with the selected image URI, for example, display it in an ImageView
                imageView.setImageURI(selectedImage);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        button = view.findViewById(R.id.jusbtnAccessPhotos);
        imageView = view.findViewById(R.id.jusimageViewSettings);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission()) {
                    openGallery();
                } else {
                    requestPermission();
                }
            }
        });
        return view;
    }

    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(galleryIntent);
    }

    private void redirectToAppInfo() {
        // Redirect the user to the app info page
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", requireActivity().getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }
}



