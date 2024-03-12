// justin chipman n01598472
package justin.chipman.n01598472;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ShareFragment extends Fragment {
    private EditText etEmail, etID;
    private CheckBox checkBox;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share, container, false);

        etEmail = view.findViewById(R.id.jusetEmail);
        etID = view.findViewById(R.id.jusetID);
        checkBox = view.findViewById(R.id.juscheckboxProfile);
        ImageButton btnSave = view.findViewById(R.id.jusimageButtonProfile);

        btnSave.setOnClickListener(v -> saveData());
        displayCurrentTimeGmt();
        return view;
    }

    private void saveData() {
        String email = etEmail.getText().toString();
        String id = etID.getText().toString();
        boolean isChecked = checkBox.isChecked();

        if (!isValidEmail(email)) {
            etEmail.setError("Invalid Email");
            return;
        }

        if (id.length() < 6) {
            etID.setError("Minimum 6 digits required");
            return;
        }

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SharedPrefs", getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("id", id);
        editor.putBoolean("checkbox", isChecked);
        editor.apply();

        Toast.makeText(getActivity(), "Email: " + email + "\nID: " + id, Toast.LENGTH_LONG).show();
        clearFields();
    }

    private void displayCurrentTimeGmt() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String gmtTime = "Justin Chipman | " + sdf.format(new Date()) + " GMT";
        Toast.makeText(getActivity(), gmtTime, Toast.LENGTH_LONG).show();
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void clearFields() {
        etEmail.setText("");
        etID.setText("");
        checkBox.setChecked(false);
    }
}
