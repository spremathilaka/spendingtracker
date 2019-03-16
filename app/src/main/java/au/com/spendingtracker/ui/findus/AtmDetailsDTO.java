package au.com.spendingtracker.ui.findus;


import android.os.Parcel;
import android.os.Parcelable;


public class AtmDetailsDTO implements Parcelable {

    private String id;

    private String name;

    private String address;

    private Double lat;

    private Double lng;

    private AtmDetailsDTO(String id, String name, String address, Double lat, Double lng) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public AtmDetailsDTO createFromParcel(Parcel in) {
            return new AtmDetailsDTO(in);
        }

        public AtmDetailsDTO[] newArray(int size) {
            return new AtmDetailsDTO[size];
        }
    };

    private AtmDetailsDTO(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.address = in.readString();
        this.lat = in.readDouble();
        this.lng = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.address);
        dest.writeDouble(this.lat);
        dest.writeDouble(this.lng);
    }


    public static class AtmDetailsDTOBuilder {
        private String id;
        private String name;
        private String address;
        private Double lat;
        private Double lng;

        public AtmDetailsDTOBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public AtmDetailsDTOBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public AtmDetailsDTOBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public AtmDetailsDTOBuilder setLat(Double lat) {
            this.lat = lat;
            return this;
        }

        public AtmDetailsDTOBuilder setLng(Double lng) {
            this.lng = lng;
            return this;
        }

        public AtmDetailsDTO createAtmDetailsDTO() {
            return new AtmDetailsDTO(id, name, address, lat, lng);
        }
    }

    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }


    public Double getLat() {
        return lat;
    }


    public Double getLng() {
        return lng;
    }


}
