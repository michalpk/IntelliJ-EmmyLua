package com.tang.intellij.lua.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.tang.intellij.lua.doc.LuaCommentUtil;
import com.tang.intellij.lua.doc.psi.api.LuaComment;
import com.tang.intellij.lua.lang.type.LuaTypeSet;
import com.tang.intellij.lua.reference.LuaNameReference;
import org.jetbrains.annotations.NotNull;

/**
 * LuaPsiImplUtil
 * Created by TangZX on 2016/11/22.
 */
public class LuaPsiImplUtil {

    public static PsiElement setName(LuaNameDef identifier, String name) {
        PsiElement newId = LuaElementFactory.createIdentifier(identifier.getProject(), name);
        PsiElement oldId = identifier.getFirstChild();
        oldId.replace(newId);
        return newId;
    }

    @NotNull
    public static String getName(LuaNameDef identifier) {
        return identifier.getText();
    }

    public static LuaTypeSet resolveType(LuaNameDef nameDef) {
        return LuaPsiResolveUtil.resolveType(nameDef);
    }

    @NotNull
    public static PsiReference getReference(LuaNameRef ref) {
        return new LuaNameReference(ref);
    }

    public static PsiElement resolve(LuaNameRef ref) {
        return LuaPsiResolveUtil.resolve(ref);
    }

    public static LuaTypeSet resolveType(LuaParDef parDef) {
        return LuaPsiResolveUtil.resolveType(parDef);
    }

    public static LuaComment getComment(LuaGlobalFuncDef globalFuncDef) {
        return LuaCommentUtil.findComment(globalFuncDef);
    }

    public static LuaComment getComment(LuaLocalFuncDef localFuncDef) {
        return LuaCommentUtil.findComment(localFuncDef);
    }

    public static LuaComment getComment(LuaLocalDef localDef) {
        return LuaCommentUtil.findComment(localDef);
    }

    public static LuaTypeSet guessType(LuaFuncCall funcCall) {
        LuaCallExpr callExpr = (LuaCallExpr)funcCall.getFirstChild();
        if (callExpr == null) return null;
        else return callExpr.guessType();
    }

    public static LuaTypeSet guessPrefixType(LuaCallExpr callExpr) {
        LuaNameRef nameRef = callExpr.getNameRef();
        if (nameRef != null) {
            PsiElement def = nameRef.resolve();
            if (def instanceof LuaTypeResolvable) {
                return ((LuaTypeResolvable) def).resolveType();
            }
        }
        else {
            LuaExpr prefix = (LuaExpr) callExpr.getFirstChild();
            if (prefix != null)
                return prefix.guessType();
        }
        return null;
    }
}
