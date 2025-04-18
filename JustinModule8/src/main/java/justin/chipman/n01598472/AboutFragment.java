// justin chipman n01598472
package justin.chipman.n01598472;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class AboutFragment extends Fragment {
    private static final String PREFS_NAME = "SharedPrefs";
    private static final String ACCESS_COUNT_KEY = "access_count";
    private static final String EMAIL_KEY = "email";
    private static final String ID_KEY = "id";
    private static final String CHECKBOX_KEY = "checkbox";
    private SharedPreferences prefs;
    private TextView tvEmailValue;
    private TextView tvIDValue;
    private TextView tvCheckboxValue;

    public AboutFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        prefs = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);


        ToggleButton toggleButton = view.findViewById(R.id.justoggleButton);
        tvEmailValue = view.findViewById(R.id.justvEmailValue);
        tvIDValue = view.findViewById(R.id.justvIDValue);
        tvCheckboxValue = view.findViewById(R.id.justvCheckboxValue);

        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            } else {
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
            }
        });

        incrementAccessCounter();
        displayData();

        return view;
    }

    private void incrementAccessCounter() {
        int accessCount = prefs.getInt(ACCESS_COUNT_KEY, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(ACCESS_COUNT_KEY, ++accessCount);
        editor.apply();

        String input = getString(R.string.accessed) + accessCount + getString(R.string.times);
        Toast.makeText(getActivity(), input, Toast.LENGTH_SHORT).show();
    }

    private void displayData() {
        boolean isChecked = prefs.getBoolean(CHECKBOX_KEY, false);
        String email = prefs.getString(EMAIL_KEY, "");
        String id = prefs.getString(ID_KEY, "");
        String input;

        if (email.isEmpty() && id.isEmpty()) {
            tvEmailValue.setText(getString(R.string.no_data));
            tvIDValue.setText(getString(R.string.no_data));
            tvCheckboxValue.setText(getString(R.string.no_data));
        } else {
            input = getString(R.string.email_colon) +  email;
            tvEmailValue.setText(input);
            input = getString(R.string.id_colon) + id;
            tvIDValue.setText(input);
            input = getString(R.string.checkbox_colon) + (isChecked ? getString(R.string.checked) : getString(R.string.unchecked));
            tvCheckboxValue.setText(input);
        }
    }
}

