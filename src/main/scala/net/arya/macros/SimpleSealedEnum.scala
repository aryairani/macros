package net.arya.macros

import scala.collection.immutable.Nil
import scala.reflect.macros.blackbox

class SimpleSealedEnum(values: Symbol*) extends scala.annotation.StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro SimpleSealedEnum.annotationMacro
}

object SimpleSealedEnum {
  def annotationMacro(c: blackbox.Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._

    val AnnotationTpe = TypeName("SimpleSealedEnum")

    val values: List[scala.Symbol] = c.macroApplication match {
      case Apply(Select(Apply(Select(New(Ident(AnnotationTpe)), t), args), _), _) if t == termNames.CONSTRUCTOR => args.map {
        case q"scala.Symbol($name)" ⇒ name match {
          case Literal(Constant(str)) ⇒ str match {
            case str: String ⇒ scala.Symbol(str)
          }
        }
      }
    }

    def instanceFromSymbol(tpname: TypeName)(s: scala.Symbol): ModuleDef = q"final case object ${TermName(s.name)} extends $tpname"
    def all(defs: List[ModuleDef]): ValDef = {
      def extract(m: ModuleDef): TermName = {
        val q"final case object $v extends $_" = m
        v
      }
      q"val values = ${defs.map(extract(_))}"
    }

    val result = annottees map (_.tree) match {
      case q"$mods trait $tpname" :: Nil =>
        val objName = tpname.toTermName

        def instances: List[ModuleDef] = values map (instanceFromSymbol(tpname)(_))

        q"""
         sealed trait $tpname extends Product with Serializable // make type inference less annoying?
         object $objName { ..$instances; ${all(instances)} }
         """
      case q"$mods trait $tpname"
        :: q"object $objName extends { ..$objEarlyDefs } with ..$objParents { $objSelf => ..$objDefs }"
        :: Nil =>
        val objName = tpname.toTermName

        def instances: List[ModuleDef] = values map (instanceFromSymbol(tpname)(_))

        q"""
         sealed trait $tpname extends Product with Serializable // make type inference less annoying?
         object $objName extends { ..$objEarlyDefs } with ..$objParents {
          $objSelf =>
            ..$instances
            ${all(instances)}
            ..$objDefs
         }
         """

      case _ => c.abort(c.enclosingPosition, "Invalid annotation target: must be a trait with no type parameters")
    }

    c.Expr[Any](result)
  }
}
