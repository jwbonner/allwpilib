#include "imgui_RobotoBold.h"
#include "RobotoBold.inc"
ImFont* ImGui::AddFontRobotoBold(ImGuiIO& io, float size_pixels, const ImFontConfig* font_cfg, const ImWchar* glyph_ranges) {
  return io.Fonts->AddFontFromMemoryCompressedTTF(RobotoBold_compressed_data, RobotoBold_compressed_size, size_pixels, font_cfg, glyph_ranges);
}
