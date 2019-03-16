package au.com.spendingtracker.data.source.model.common;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static au.com.spendingtracker.data.source.model.common.Status.ERROR;
import static au.com.spendingtracker.data.source.model.common.Status.LOADING;

public class ApiResponse<T> {

    private final Status status;

    @Nullable
    private final T data;

    @Nullable
    private final Throwable throwable;

    public ApiResponse(Status status, @Nullable T data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.throwable = error;
    }

    public  static ApiResponse loading() {
        return new ApiResponse(LOADING, null, null);
    }

//    public  static ApiResponse success(@NonNull T data) {
//        return new ApiResponse(SUCCESS, data, null);
//    }

    public  static ApiResponse error(@NonNull Throwable throwable) {
        return new ApiResponse(ERROR, null, throwable);
    }

    public Status getStatus() {
        return status;
    }

    @Nullable
    public Throwable getThrowable() {
        return throwable;
    }

    @Nullable
    public T getData() {
        return data;
    }
}
