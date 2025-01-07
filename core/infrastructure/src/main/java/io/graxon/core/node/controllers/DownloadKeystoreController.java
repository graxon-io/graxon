package io.graxon.core.node.controllers;

import io.graxon.core.usecases.node.DownloadKeystoreUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 */
@Tag(name = "Node")
@RestController
@RequestMapping("/api/v1/clusters/{cluster_id}/nodes/{node_id}/keystore")
public class DownloadKeystoreController {
    // --------------------------------------------------------------------------------------------

    //
    private final DownloadKeystoreUseCase useCase;

    /**
     *
     */
    public DownloadKeystoreController(DownloadKeystoreUseCase useCase) {
        this.useCase = useCase;
    }

    /**
     * Download the keystore for the given cluster
     *
     * @param clusterId the cluster id
     * @param nodeId    the node id
     * @return the keystore
     * @throws IOException if an error occurs
     */
    @GetMapping
    public ResponseEntity<Resource> downloadKeystore(
        @PathVariable("cluster_id") String clusterId,
        @PathVariable("node_id") String nodeId) throws IOException {
        var resource = useCase.execute(clusterId, nodeId);
        var file = resource.getFile();
        var stream = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .contentLength(file.length())
            .header("Content-Disposition", "attachment; filename=\"keystore.p12\"")
            .body(stream);
    }

}
