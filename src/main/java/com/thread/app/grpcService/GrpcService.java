/*
package com.thread.app.grpcService;

import com.google.protobuf.Empty;
import com.thread.app.dto.ProductDTO;
import com.thread.app.mapper.ProductMapper;
import com.thread.app.proto.*;
import com.thread.app.service.ProductService;
import io.grpc.stub.StreamObserver;
import jakarta.inject.Singleton;

@Singleton
public class GrpcService extends InventoryServiceGrpc.InventoryServiceImplBase {
    private final ProductService productService;

    public GrpcService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void createProduct(CreateProductRequest request ,
                              StreamObserver<ProductResponse>  responseObserver) {
        ProductDTO  productDTO = ProductMapper.fromGrpc(request);
        ProductDTO created = productService.createProduct(productDTO);
        responseObserver.onNext(ProductMapper.toGrpc(created));
        responseObserver.onCompleted();
    }

    @Override
    public void getProductId(GetProductRequest request,
                           StreamObserver<ProductResponse>  responseObserver) {
        ProductDTO productDTO = productService.getProductById(request.getId());
        responseObserver.onNext(ProductMapper.toGrpc(productDTO));
        responseObserver.onCompleted();
    }

    @Override
    public void getProductName(GetProductRequestName request,
                               StreamObserver<ProductResponse>  responseObserver) {
        ProductDTO productDTO = productService.getProductByName(request.getName());
        responseObserver.onNext(ProductMapper.toGrpc(productDTO));
        responseObserver.onCompleted();
    }

    @Override
    public void updateStock(UpdateStockRequest request,
                            StreamObserver<ProductResponse>  responseObserver) {
        ProductDTO productDTO = productService.updateStock(request.getId(), request.getStock());
        responseObserver.onNext(ProductMapper.toGrpc(productDTO));
        responseObserver.onCompleted();
    }

    @Override
    public void updateProductName(UpdateProductNameRequest requestName,
                                StreamObserver<ProductResponse> responseStreamObserver){
        ProductDTO updated = productService.updateProductName(
                requestName.getName(),
                requestName.getId()
        );
        responseStreamObserver.onNext(ProductMapper.toGrpc(updated));
        responseStreamObserver.onCompleted();
    }

    @Override
    public void deleteProduct(DeleteProductRequest request, StreamObserver<Empty> response) {
        productService.deleteProductById(request.getId());
        response.onNext(Empty.getDefaultInstance());
        response.onCompleted();
    }

    @Override
    public void listProducts(ListProductsRequest request,
                             StreamObserver<ListProductsResponse>  responseObserver) {
        ListProductsResponse.Builder out =  ListProductsResponse.newBuilder();

        productService.listAllProducts()
                .forEach(productDTO -> out.addProduct(ProductMapper.toGrpc(productDTO)));

        responseObserver.onNext(out.build());
        responseObserver.onCompleted();
    }
}
*/
