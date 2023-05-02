package com.example.signalussample1_java.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.core.os.BundleKt;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.signalussample1_java.R;
import com.example.signalussample1_java.R.id;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
        mv = {1, 7, 1},
        k = 1,
        d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ\u0012\u0010\u000e\u001a\u00020\u000b2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0016J&\u0010\u0011\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J\u001a\u0010\u0018\u001a\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\u00102\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\u001a"},
        d2 = {"Lcom/example/signalussample1/fragment/secondSelectFaceFragment;", "Landroidx/fragment/app/Fragment;", "Landroid/view/View$OnClickListener;", "()V", "navController", "Landroidx/navigation/NavController;", "getNavController", "()Landroidx/navigation/NavController;", "setNavController", "(Landroidx/navigation/NavController;)V", "navigateWithValue", "", "index", "", "onClick", "v", "Landroid/view/View;", "onCreateView", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onViewCreated", "view", "app_debug"}
)
public final class secondSelectFaceFragment extends Fragment implements View.OnClickListener {
    public NavController navController;
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

    @Nullable
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        return inflater.inflate(R.layout.fragment_second_select_face, container, false);
    }

    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        this.navController = Navigation.findNavController(view);
        ((ImageView)this._$_findCachedViewById(id.back_btn)).setOnClickListener((View.OnClickListener)this);
        ((ImageView)this._$_findCachedViewById(id.head_shadow)).setOnClickListener((View.OnClickListener)this);
        ((ImageView)this._$_findCachedViewById(id.eye_shadow)).setOnClickListener((View.OnClickListener)this);
        ((ImageView)this._$_findCachedViewById(id.mouth_shadow)).setOnClickListener((View.OnClickListener)this);
        ((ImageView)this._$_findCachedViewById(id.nose_shadow)).setOnClickListener((View.OnClickListener)this);
        ((ImageView)this._$_findCachedViewById(id.ear_left_shadow)).setOnClickListener((View.OnClickListener)this);
        ((ImageView)this._$_findCachedViewById(id.ear_right_shadow)).setOnClickListener((View.OnClickListener)this);
        ((ImageView)this._$_findCachedViewById(id.neck_shadow)).setOnClickListener((View.OnClickListener)this);
    }

    public void onClick(@Nullable View v) {
        Integer var2 = v != null ? v.getId() : null;
        int var3 = 2131230963;
        if (var2 != null) {
            if (var2 == var3) {
                this.navigateWithValue("머리");
                return;
            }
        }

        var3 = 2131230930;
        if (var2 != null) {
            if (var2 == var3) {
                this.navigateWithValue("눈");
                return;
            }
        }

        var3 = 2131231054;
        if (var2 != null) {
            if (var2 == var3) {
                this.navigateWithValue("입");
                return;
            }
        }

        var3 = 2131231099;
        if (var2 != null) {
            if (var2 == var3) {
                this.navigateWithValue("코");
                return;
            }
        }

        var3 = 2131230914;
        if (var2 != null) {
            if (var2 == var3) {
                this.navigateWithValue("왼쪽 귀");
                return;
            }
        }

        var3 = 2131230915;
        if (var2 != null) {
            if (var2 == var3) {
                this.navigateWithValue("오른쪽 귀");
                return;
            }
        }

        var3 = 2131231090;
        if (var2 != null) {
            if (var2 == var3) {
                this.navigateWithValue("목");
                return;
            }
        }

        var3 = 2131230827;
        if (var2 != null) {
            if (var2 == var3) {
                NavController var10000 = this.navController;
                if (var10000 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("navController");
                }

                var10000.popBackStack();
            }
        }

    }

    public final void navigateWithValue(@NotNull String index) {
        Intrinsics.checkNotNullParameter(index, "index");
        Bundle bundle = BundleKt.bundleOf(new Pair[]{TuplesKt.to("body_part", index)});
        NavController var10000 = this.navController;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("navController");
        }

        var10000.navigate(R.id.action_secondSelectFaceFragment_to_cameraFragment
                , bundle);
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
