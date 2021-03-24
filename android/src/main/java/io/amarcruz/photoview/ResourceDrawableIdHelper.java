package io.amarcruz.photoview;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.facebook.common.util.UriUtil;

/**
 * Helper class for obtaining information about local images.
 */
class ResourceDrawableIdHelper {

  private Map<String, Integer> mResourceDrawableIdMap;

  ResourceDrawableIdHelper() {
    mResourceDrawableIdMap = new HashMap<>();
  }

  private int getResourceDrawableId(Context context, @Nullable String name) {
    if (name == null || name.isEmpty()) {
      return 0;
    }

    name = name.toLowerCase().replace("-", "_");
    if (mResourceDrawableIdMap.containsKey(name)) {
      Integer _id = mResourceDrawableIdMap.get(name);
      if (_id != null) {
        return _id;
      }
    }

    int id = context.getResources().getIdentifier(
        name,
        "drawable",
        context.getPackageName());
    mResourceDrawableIdMap.put(name, id);
    return id;
  }

  @Nullable Drawable getResourceDrawable(Context context, @Nullable String name) {
    int resId = getResourceDrawableId(context, name);
    return resId > 0 ? context.getResources().getDrawable(resId) : null;
  }

  Uri getResourceDrawableUri(Context context, @Nullable String name) {
    int resId = getResourceDrawableId(context, name);
    return resId > 0 ? new Uri.Builder()
        .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
        .path(String.valueOf(resId))
        .build() : Uri.EMPTY;
  }
}
