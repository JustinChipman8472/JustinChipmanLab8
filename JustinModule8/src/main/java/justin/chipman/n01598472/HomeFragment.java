// justin chipman n01598472
package justin.chipman.n01598472;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.snackbar.Snackbar;

public class HomeFragment extends Fragment {
    private int imageClickCount = 0;
    private ImageView imageView;
    private final int[] images = {R.drawable.ichigo, R.drawable.asta, R.drawable.teacher, R.drawable.captain};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        imageView = view.findViewById(R.id.jusimageView);
        Button buttonChangeImage = view.findViewById(R.id.jusbtnChangeImage);

        buttonChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageClickCount = (imageClickCount + 1) % images.length;
                imageView.setImageResource(images[imageClickCount]);
                String input = getString(R.string.justin_chipman_clicks);

                Snackbar.make(view, input + (imageClickCount + 1),
                        Snackbar.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
