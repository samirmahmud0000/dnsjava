// SPDX-License-Identifier: BSD-3-Clause
// -*- Java -*-
//
// Copyright (c) 2005, Matthew J. Rutherford <rutherfo@cs.colorado.edu>
// Copyright (c) 2005, University of Colorado at Boulder
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are
// met:
//
// * Redistributions of source code must retain the above copyright
//   notice, this list of conditions and the following disclaimer.
//
// * Redistributions in binary form must reproduce the above copyright
//   notice, this list of conditions and the following disclaimer in the
//   documentation and/or other materials provided with the distribution.
//
// * Neither the name of the University of Colorado at Boulder nor the
//   names of its contributors may be used to endorse or promote
//   products derived from this software without specific prior written
//   permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
// "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
// LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
// A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
// OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
// SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
// LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
// DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
// THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
// OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
//
package org.xbill.DNS;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import org.junit.jupiter.api.Test;

class NSAP_PTRRecordTest {
  @Test
  void ctor_0arg() {
    NSAP_PTRRecord d = new NSAP_PTRRecord();
    assertNull(d.getName());
    assertNull(d.getTarget());
  }

  @Test
  void ctor_4arg() throws TextParseException {
    Name n = Name.fromString("my.name.");
    Name a = Name.fromString("my.alias.");

    NSAP_PTRRecord d = new NSAP_PTRRecord(n, DClass.IN, 0xABCDEL, a);
    assertEquals(n, d.getName());
    assertEquals(Type.NSAP_PTR, d.getType());
    assertEquals(DClass.IN, d.getDClass());
    assertEquals(0xABCDEL, d.getTTL());
    assertEquals(a, d.getTarget());
  }

  @Test
  void rdataFromString() throws IOException {
    Tokenizer t = new Tokenizer("foo.bar.com.");
    NSAP_PTRRecord nsapPtr = new NSAP_PTRRecord();
    nsapPtr.rdataFromString(t, null);
    assertEquals(Name.fromConstantString("foo.bar.com."), nsapPtr.getTarget());
  }
}
