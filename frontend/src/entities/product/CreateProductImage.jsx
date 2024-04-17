import React, { useState } from 'react'
import { useForm } from "react-hook-form";
import { baseApi } from '../../shared/api/baseApi';
import { useDispatch } from 'react-redux';

export const createProductApi = baseApi.injectEndpoints({
    endpoints: (builder) => ({
      createProduct: builder.mutation({
        query: (product) => ({
          url: "/products/images/1051",
          method: "POST",
          body: { ...product },
          withCredentials: true,
          prepareHeaders: (headers) => {
            headers.set("Content-Type", "multipart/form-data")
              return headers
          },
        }),
      }),
    }),
  });

export const {useCreateProductMutation} = createProductApi  

export const CreateProductImage = () => {

    const { register, handleSubmit } = useForm();
    const dispatch = useDispatch()
    const [createProduct] = useCreateProductMutation();
    const [imageByte, setImageByte] = useState();


    const onSubmit = async (data) => {
        const formData = new FormData();
        formData.append("file", data.file[0]);
    
        console.log(data.file[0]);

        //const response = await createProduct(formData).unwrap();

        await fetch("http://localhost:8080/api/v1/products/images/1051", {
          method: "POST",
          body: formData,
            // headers: {
            //     "Content-Type": "multipart/form-data",
            //     // "Content-Length":"<calculated when request is sent>"
            //   },
        })

        const res = await fetch("http://localhost:8080/api/v1/products/images/1051", {
            method: "GET",
          })

        const imageBlob = await res.blob();
        const imageUrl = URL.createObjectURL(imageBlob);
        setImageByte(imageUrl);  
        
        
      };
    
      return (
        <div className="createProductImage">
          <form onSubmit={handleSubmit(onSubmit)}>
            <input type="file" {...register("file")} />
    
            <input type="submit" />
          </form>
          {imageByte && <img src={imageByte} alt="Imfdage" />}
        </div>
      );
}
