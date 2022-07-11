package com.example.baseproject.databinding;
import com.example.baseproject.R;
import com.example.baseproject.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ItemLayoutBindingImpl extends ItemLayoutBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ItemLayoutBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds));
    }
    private ItemLayoutBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ImageView) bindings[1]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[3]
            );
        this.ivAppIcon.setTag(null);
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tvAppName.setTag(null);
        this.tvAppUsage.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.appModel == variableId) {
            setAppModel((com.example.baseproject.model.AppModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setAppModel(@Nullable com.example.baseproject.model.AppModel AppModel) {
        this.mAppModel = AppModel;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.appModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        java.lang.String appModelAppName = null;
        com.example.baseproject.model.AppModel appModel = mAppModel;
        android.graphics.drawable.Drawable appModelAppIcon = null;
        java.lang.String appModelAppUsageString = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (appModel != null) {
                    // read appModel.appName
                    appModelAppName = appModel.getAppName();
                    // read appModel.appIcon
                    appModelAppIcon = appModel.getAppIcon();
                    // read appModel.appUsageString
                    appModelAppUsageString = appModel.getAppUsageString();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            com.example.baseproject.utils.ImageUtils.setAppIcon(this.ivAppIcon, appModelAppIcon);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvAppName, appModelAppName);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvAppUsage, appModelAppUsageString);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): appModel
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}