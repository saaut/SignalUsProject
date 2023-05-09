package com.example.signalussample1_java.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.signalussample1_java.MainActivity;
import com.example.signalussample1_java.R;
import com.example.signalussample1_java.R.id;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
        mv = {1, 7, 1},
        k = 1,
        d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0012\u0010\u0014\u001a\u00020\u00112\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0016J&\u0010\u0017\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J\u001a\u0010\u001e\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\u00162\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f¨\u0006 "},
        d2 = {"Lcom/example/signalussample1/fragment/MainTitleFragment;", "Landroidx/fragment/app/Fragment;", "Landroid/view/View$OnClickListener;", "()V", "mainActivity", "Lcom/example/signalussample1/MainActivity;", "getMainActivity", "()Lcom/example/signalussample1/MainActivity;", "setMainActivity", "(Lcom/example/signalussample1/MainActivity;)V", "navController", "Landroidx/navigation/NavController;", "getNavController", "()Landroidx/navigation/NavController;", "setNavController", "(Landroidx/navigation/NavController;)V", "onAttach", "", "context", "Landroid/content/Context;", "onClick", "v", "Landroid/view/View;", "onCreateView", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onViewCreated", "view", "app_debug"}
)
public final class MainTitleFragment extends Fragment implements View.OnClickListener {
    public NavController navController;
    public MainActivity mainActivity;
    private HashMap _$_findViewCache;

    @NotNull
    public final NavController getNavController() {
        NavController var10000 = this.navController;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("navController");
        }

        return var10000;
    }

    public final void setNavController(@NotNull NavController var1) {
        Intrinsics.checkNotNullParameter(var1, "<set-?>");
        this.navController = var1;
    }

    @NotNull
    public final MainActivity getMainActivity() {
        MainActivity var10000 = this.mainActivity;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mainActivity");
        }

        return var10000;
    }

    public final void setMainActivity(@NotNull MainActivity var1) {
        Intrinsics.checkNotNullParameter(var1, "<set-?>");
        this.mainActivity = var1;
    }

    public void onAttach(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        super.onAttach(context);
        this.mainActivity = (MainActivity)context;
    }

    @Nullable
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        return inflater.inflate(R.layout.fragment_main_title, container, false);
    }

    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        this.navController = Navigation.findNavController(view);
        ((ImageView)this._$_findCachedViewById(id.maincharacter_img)).setOnClickListener((View.OnClickListener)this);
        ((ImageView)this._$_findCachedViewById(id.SignalUSTitleimageView)).setOnClickListener((View.OnClickListener)this);
    }

    public void onClick(@Nullable View v) {
        Integer var2 = v != null ? v.getId() : null;
        int var3 = id.maincharacter_img;
        if (var2 != null) {
            if (var2 == var3) {
                NavController var4 = this.navController;
                if (var4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("navController");
                }

                var4.navigate(R.id.action_mainTitleFragment_to_firstSelectFragment);
                return;
            }
        }

        var3 = id.SignalUSTitleimageView;
        if (var2 != null) {
            if (var2 == var3) {
                TextView var10000 = (TextView)this._$_findCachedViewById(id.information_text);
                Intrinsics.checkNotNullExpressionValue(var10000, "information_text");
                var10000.setText((CharSequence)"시그널 어스");

            }
        }
    }

    public View _$_findCachedViewById(int var1) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }

        View var2 = (View)this._$_findViewCache.get(var1);
        if (var2 == null) {
            View var10000 = this.getView();
            if (var10000 == null) {
                return null;
            }

            var2 = var10000.findViewById(var1);
            this._$_findViewCache.put(var1, var2);
        }

        return var2;
    }

    public void _$_clearFindViewByIdCache() {
        if (this._$_findViewCache != null) {
            this._$_findViewCache.clear();
        }

    }

    // $FF: synthetic method
    public void onDestroyView() {
        super.onDestroyView();
        this._$_clearFindViewByIdCache();
    }
}
