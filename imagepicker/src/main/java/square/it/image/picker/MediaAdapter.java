package square.it.image.picker;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView.RecyclerListener;
import android.widget.RelativeLayout;

import androidx.cursoradapter.widget.CursorAdapter;

import java.util.ArrayList;
import java.util.List;

import square.it.image.picker.imageloader.MediaImageLoader;
import square.it.image.picker.utils.MediaUtils;
import square.it.image.picker.widget.PickerImageView;

/**
 * @author TUNGDX
 */

/**
 * Adapter for display media item list.
 */
public class MediaAdapter extends CursorAdapter implements RecyclerListener {
    private MediaImageLoader mMediaImageLoader;
    private List<MediaItem> mMediaListSelected = new ArrayList<MediaItem>();
    private MediaOptions mMediaOptions;
    private int mItemHeight = 0;
    private int mNumColumns = 0;
    private RelativeLayout.LayoutParams mImageViewLayoutParams;
    private List<PickerImageView> mPickerImageViewSelected = new ArrayList<PickerImageView>();

    public MediaAdapter(Context context, Cursor c, int flags,
                        MediaImageLoader mediaImageLoader, MediaOptions mediaOptions) {
        this(context, c, flags, null, mediaImageLoader, mediaOptions);
    }

    public MediaAdapter(Context context, Cursor c, int flags,
                        List<MediaItem> mediaListSelected, MediaImageLoader mediaImageLoader, MediaOptions mediaOptions) {
        super(context, c, flags);
        if (mediaListSelected != null)
            mMediaListSelected = mediaListSelected;
        mMediaImageLoader = mediaImageLoader;
        mMediaOptions = mediaOptions;
        mImageViewLayoutParams = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final ViewHolder holder = (ViewHolder) view.getTag();
        final Uri uri;
        uri = MediaUtils.getPhotoUri(cursor);
        holder.thumbnail.setVisibility(View.GONE);
        boolean isSelected = isSelected(uri);
        holder.imageView.setSelected(isSelected);
        if (isSelected) {
            mPickerImageViewSelected.add(holder.imageView);
        }
        mMediaImageLoader.displayImage(uri, holder.imageView);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        ViewHolder holder = new ViewHolder();
        View root = View
                .inflate(context, R.layout.list_item_mediapicker, null);
        holder.imageView = (PickerImageView) root.findViewById(R.id.thumbnail);
        holder.thumbnail = root.findViewById(R.id.overlay);

        holder.imageView.setLayoutParams(mImageViewLayoutParams);
        // Check the height matches our calculated column width
        if (holder.imageView.getLayoutParams().height != mItemHeight) {
            holder.imageView.setLayoutParams(mImageViewLayoutParams);
        }
        root.setTag(holder);
        return root;
    }

    private class ViewHolder {
        PickerImageView imageView;
        View thumbnail;
    }

    public boolean hasSelected() {
        return mMediaListSelected.size() > 0;
    }

    /**
     * Check media uri is selected or not.
     *
     * @param uri Uri of media item (photo, video)
     * @return true if selected, false otherwise.
     */
    public boolean isSelected(Uri uri) {
        if (uri == null)
            return false;
        for (MediaItem item : mMediaListSelected) {
            if (item.getUriOrigin().equals(uri))
                return true;
        }
        return false;
    }

    /**
     * Check {@link MediaItem} is selected or not.
     *
     * @param item {@link MediaItem} to check.
     * @return true if selected, false otherwise.
     */
    public boolean isSelected(MediaItem item) {
        return mMediaListSelected.contains(item);
    }

    /**
     * Set {@link MediaItem} selected.
     *
     * @param item {@link MediaItem} to selected.
     */
    public void setMediaSelected(MediaItem item) {
        syncMediaSelectedAsOptions();
        if (!mMediaListSelected.contains(item))
            mMediaListSelected.add(item);
    }

    /**
     * If item selected then change to unselected and unselected to selected.
     *
     * @param item Item to update.
     */
    public void updateMediaSelected(MediaItem item,
                                    PickerImageView pickerImageView) {
        if (mMediaListSelected.contains(item)) {
            mMediaListSelected.remove(item);
            pickerImageView.setSelected(false);
            this.mPickerImageViewSelected.remove(pickerImageView);
        } else {
            boolean value = syncMediaSelectedAsOptions();
            if (value) {
                for (PickerImageView picker : this.mPickerImageViewSelected) {
                    picker.setSelected(false);
                }
                this.mPickerImageViewSelected.clear();
            }
            mMediaListSelected.add(item);
            pickerImageView.setSelected(true);
            this.mPickerImageViewSelected.add(pickerImageView);
        }
    }

    /**
     * @return List of {@link MediaItem} selected.
     */
    public List<MediaItem> getMediaSelectedList() {
        return mMediaListSelected;
    }

    /**
     * Set list of {@link MediaItem} selected.
     *
     * @param list
     */
    public void setMediaSelectedList(List<MediaItem> list) {
        mMediaListSelected = list;
    }

    /**
     * Whether clear or not media selected as options.
     *
     * @return true if clear, false otherwise.
     */
    private boolean syncMediaSelectedAsOptions() {
        if (!mMediaOptions.canSelectMultiPhoto()) {
            mMediaListSelected.clear();
            return true;
        }
        return false;
    }

    // set numcols
    public void setNumColumns(int numColumns) {
        mNumColumns = numColumns;
    }

    public int getNumColumns() {
        return mNumColumns;
    }

    // set photo item height
    public void setItemHeight(int height) {
        if (height == mItemHeight) {
            return;
        }
        mItemHeight = height;
        mImageViewLayoutParams.height = height;
        mImageViewLayoutParams.width = height;
        notifyDataSetChanged();
    }

    @Override
    public void onMovedToScrapHeap(View view) {
        PickerImageView imageView = (PickerImageView) view
                .findViewById(R.id.thumbnail);
        mPickerImageViewSelected.remove(imageView);
    }

    public void onDestroyView() {
        mPickerImageViewSelected.clear();
    }
}