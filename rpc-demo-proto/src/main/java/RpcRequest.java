import lombok.Data;

@Data
public class RpcRequest {
    private ServiceDescriptor service;
    private Object[] parameters;
}
