#!groovy

/*
 * This script configures the simple theme plugin.
 * Requires the simple theme plugin to be installed.
 * Tested with simple-theme-plugin:0.5.1
 *
 * Use http://afonsof.com/jenkins-material-theme/ to generate a new jenkins theme.
 * Upload the theme to a CDN or place it directly at the userContent directory of 
 * Jenkins to be publicly available.
 */

import jenkins.model.Jenkins
import org.jenkinsci.plugins.simpletheme.CssUrlThemeElement
import org.jenkinsci.plugins.simpletheme.JsUrlThemeElement
import org.jenkinsci.plugins.simpletheme.FaviconUrlThemeElement


def themeDecorator = Jenkins.instance.getExtensionList(org.codefirst.SimpleThemeDecorator.class).first()

themeDecorator.setElements([
  new JsUrlThemeElement('https://cdn.rawgit.com/djonsson/jenkins-atlassian-theme/gh-pages/theme.js'),
  new CssUrlThemeElement('https://cdn.rawgit.com/djonsson/jenkins-atlassian-theme/gh-pages/theme-min.css'),
  new FaviconUrlThemeElement('https://cdn.rawgit.com/djonsson/jenkins-atlassian-theme/gh-pages/jenkins_logo.png')
])

Jenkins.instance.save()