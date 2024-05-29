package tech.reactivemedia.billingsvc.billings

import io.micronaut.core.annotation.NonNull
import io.micronaut.test.support.TestPropertyProvider
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Path

abstract class BaseSpec extends Specification implements TestPropertyProvider {

    @Override
    @NonNull
    Map<String, String> getProperties() {
        Path tempDir = Files.createTempDirectory('testdata-ms')
        ["microstream.storage.main.storage-directory": tempDir.toString()]
    }
}
