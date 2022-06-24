package com.promineotech.timeline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.promineotech.ComponentScanMarker;

/**
 * @author JamesR
 *
 */
@SpringBootApplication(scanBasePackageClasses = { ComponentScanMarker.class })

public class Timeline {
  public static void main(String[] args) {
    SpringApplication.run(Timeline.class, args);
  }
}