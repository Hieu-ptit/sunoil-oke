package com.contract_debt.util;

import com.common.model.response.Response;
import com.common.util.Json;
import com.contract_debt.model.request.IncrementLimitRequest;
import com.contract_debt.model.response.*;
import com.dslplatform.json.JsonReader;
import com.dslplatform.json.JsonWriter;
import com.dslplatform.json.runtime.Generics;

import java.util.Arrays;
import java.util.List;

public class Global {

    public Global() {
    }

    public final static String CONTRACT_CODE_NO_SEQUENCE = "contract_code_no_seq";

    public final static List<String> fieldSortVehicle = Arrays.asList("name", "code", "created_at", "contract_type", "effect_end_date", "status");

    public static final JsonWriter.WriteObject<IncrementLimitRequest> incrementLimitWriter =
            Json.findWriter(IncrementLimitRequest.class);

    public static final JsonReader.ReadObject<Response<ProfileResponse>> profileReaders =
            Json.findReader(Generics.makeParameterizedType(Response.class, ProfileResponse.class));

    public static final JsonReader.ReadObject<Response<ProductCategoryResponse>> productReaders =
            Json.findReader(Generics.makeParameterizedType(Response.class, ProductCategoryResponse.class));

    public static final JsonReader.ReadObject<Response<PropertiesResponse>> propertiesReaders =
            Json.findReader(Generics.makeParameterizedType(Response.class, PropertiesResponse.class));

    public static final JsonReader.ReadObject<Response<Boolean>> driverReaders =
            Json.findReader(Generics.makeParameterizedType(Response.class, Boolean.class));

    public static final JsonReader.ReadObject<Response<StationResponse>> stationReader =
            Json.findReader(Generics.makeParameterizedType(Response.class, StationResponse.class));

    public static final JsonReader.ReadObject<Response<FileResponse>> fileReader =
            Json.findReader(Generics.makeParameterizedType(Response.class, FileResponse.class));

    public static final JsonReader.ReadObject<Response<Boolean>> booleanReader =
            Json.findReader(Generics.makeParameterizedType(Response.class, Boolean.class));

    public static final JsonReader.ReadObject<Response<DetailEmployeeResponse>> detailEmployeeReader =
            Json.findReader(Generics.makeParameterizedType(Response.class, DetailEmployeeResponse.class));

    public static final JsonReader.ReadObject<List<ProductCategoryResponse>> listProductReader =
            Json.findReader(Generics.makeParameterizedType(List.class, ProductCategoryResponse.class));

}
