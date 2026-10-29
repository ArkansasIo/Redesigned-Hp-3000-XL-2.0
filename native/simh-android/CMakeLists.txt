cmake_minimum_required(VERSION 3.22.1)
project(simh_android_bridge LANGUAGES C CXX)

# This target is only the JNI boundary scaffold. It deliberately does not
# claim to contain or execute SIMH until the upstream machine/core sources
# and Android host adapters are integrated and tested.
add_library(simh_android_bridge SHARED
    src/simh_android_bridge.cpp
)

target_include_directories(simh_android_bridge PRIVATE include)
target_compile_features(simh_android_bridge PRIVATE cxx_std_17)
target_compile_options(simh_android_bridge PRIVATE -Wall -Wextra -Wpedantic)

find_library(android_log_lib log)
if(android_log_lib)
    target_link_libraries(simh_android_bridge PRIVATE ${android_log_lib})
endif()
