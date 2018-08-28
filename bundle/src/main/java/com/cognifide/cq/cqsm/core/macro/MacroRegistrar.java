/*
 * ========================LICENSE_START=================================
 * AEM Permission Management
 * %%
 * Copyright (C) 2013 Cognifide Limited
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =========================LICENSE_END==================================
 */

package com.cognifide.cq.cqsm.core.macro;

import com.cognifide.apm.antlr.ApmLangParser.ApmContext;
import com.cognifide.apm.antlr.ApmLangParser.MacroDefinitionContext;
import com.cognifide.cq.cqsm.core.antlr.ListBaseVisitor;
import com.cognifide.cq.cqsm.core.loader.ScriptTree;
import java.util.Collections;
import java.util.List;

public class MacroRegistrar {

  public MacroRegister buildMacroRegister(ScriptTree scriptTree) {
    MacroRegister macroRegister = findMacroDefinitions(new MacroRegister(), scriptTree.getRoot());
    for (ApmContext reference : scriptTree.getIncludedScripts()) {
      findMacroDefinitions(macroRegister, reference);
    }
    return macroRegister;
  }

  private MacroRegister findMacroDefinitions(MacroRegister register, ApmContext script) {
    MacroDefinitionFinder finder = new MacroDefinitionFinder();
    List<MacroDefinitionContext> macroDefinitions = finder.visit(script);
    for (MacroDefinitionContext macroDefinition : macroDefinitions) {
      MacroDefinition macro = MacroDefinition.of(macroDefinition);
      register.put(macro.getName(), macro);
    }
    return register;
  }

  private static class MacroDefinitionFinder extends ListBaseVisitor<MacroDefinitionContext> {

    @Override
    public List<MacroDefinitionContext> visitMacroDefinition(MacroDefinitionContext ctx) {
      return Collections.singletonList(ctx);
    }
  }
}
