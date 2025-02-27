// SPDX-License-Identifier: BSD-3-Clause
// Copyright (c) 1999-2004 Brian Wellington (bwelling@xbill.org)
package org.xbill.DNS;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

/**
 * DNS extension options, as described in RFC 6891. The rdata of an OPT record is defined as a list
 * of options; this represents a single option.
 *
 * @author Brian Wellington
 * @author Ming Zhou &lt;mizhou@bnivideo.com&gt;, Beaumaris Networks
 */
public abstract class EDNSOption implements Serializable {

  /**
   * @see <a
   *     href="https://www.iana.org/assignments/dns-parameters/dns-parameters.xhtml#dns-parameters-11">IANA
   *     DNS EDNS0 Option Codes (OPT)</a>
   */
  public static class Code {
    private Code() {}

    /**
     * Apple's DNS Long-Lived Queries protocol.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc8764/">RFC 8764</a>
     */
    public static final int LLQ = 1;

    /**
     * Dynamic DNS Update Leases.
     *
     * @see <a
     *     href="https://datatracker.ietf.org/doc/html/draft-sekar-dns-ul-03">draft-sekar-dns-ul-03</a>
     */
    public static final int UL = 2;

    /**
     * Name Server Identifier.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc5001/">RFC 5001</a>
     */
    public static final int NSID = 3;

    /**
     * DNSSEC Algorithm Understood (DAU).
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc6975/">RFC 6975</a>
     */
    public static final int DAU = 5;

    /**
     * DNSSEC DS Hash Understood (DHU).
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc8764/">RFC 8764</a>
     */
    public static final int DHU = 6;

    /**
     * DNSSEC NSEC3 Hash Understood (N3U).
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc6975/">RFC 6975</a>
     */
    public static final int N3U = 7;

    /**
     * Client Subnet.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc7871/">RFC 7871</a>
     */
    public static final int CLIENT_SUBNET = 8;

    /**
     * (EDNS) EXPIRE Option.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc7314/">RFC 7314</a>
     */
    public static final int EDNS_EXPIRE = 9;

    /**
     * Cookie.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc7873/">RFC 7873</a>
     */
    public static final int COOKIE = 10;

    /**
     * TCP Keepalive.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc7828/">RFC 7828</a>
     */
    public static final int TCP_KEEPALIVE = 11;

    /**
     * EDNS(0) Padding Option.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc7830/">RFC 7830</a>
     */
    public static final int PADDING = 12;

    /**
     * CHAIN Query Requests in DNS.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc7901/">RFC 7901</a>
     */
    public static final int CHAIN = 13;

    /**
     * Signaling Trust Anchor Knowledge in DNS Security Extensions (DNSSEC).
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc8145/">RFC 8145</a>
     */
    public static final int EDNS_KEY_TAG = 14;

    /**
     * Extended DNS Errors.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc8914/">RFC 8914</a>
     */
    public static final int EDNS_EXTENDED_ERROR = 15;

    /**
     * DNS EDNS Tags.
     *
     * @see <a
     *     href="https://datatracker.ietf.org/doc/html/draft-bellis-dnsop-edns-tags-01">draft-bellis-dnsop-edns-tags-01</a>
     */
    public static final int EDNS_CLIENT_TAG = 16;

    /**
     * DNS EDNS Tags.
     *
     * @see <a
     *     href="https://datatracker.ietf.org/doc/html/draft-bellis-dnsop-edns-tags-01">draft-bellis-dnsop-edns-tags-01</a>
     */
    public static final int EDNS_SERVER_TAG = 17;

    /**
     * Report Channel.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc9567/">RFC 9567</a>
     * @since 3.6
     */
    public static final int REPORT_CHANNEL = 18;

    private static final Mnemonic codes =
        new Mnemonic("EDNS Option Codes", Mnemonic.CASE_SENSITIVE);

    static {
      codes.setMaximum(0xFFFF);
      codes.setPrefix("CODE");
      codes.setNumericAllowed(true);

      codes.add(LLQ, "LLQ");
      codes.add(UL, "UL");
      codes.add(NSID, "NSID");

      codes.add(DAU, "DAU");
      codes.add(DHU, "DHU");
      codes.add(N3U, "N3U");
      codes.add(CLIENT_SUBNET, "edns-client-subnet");
      codes.add(EDNS_EXPIRE, "EDNS_EXPIRE");
      codes.add(COOKIE, "COOKIE");
      codes.add(TCP_KEEPALIVE, "edns-tcp-keepalive");
      codes.add(PADDING, "Padding");
      codes.add(CHAIN, "CHAIN");
      codes.add(EDNS_KEY_TAG, "edns-key-tag");
      codes.add(EDNS_EXTENDED_ERROR, "Extended_DNS_Error");
      codes.add(EDNS_CLIENT_TAG, "EDNS-Client-Tag");
      codes.add(EDNS_SERVER_TAG, "EDNS-Server-Tag");
      codes.add(REPORT_CHANNEL, "Report-Channel");
    }

    /** Converts an EDNS Option Code into its textual representation */
    public static String string(int code) {
      return codes.getText(code);
    }

    /**
     * Converts a textual representation of an EDNS Option Code into its numeric value.
     *
     * @param s The textual representation of the option code
     * @return The option code, or -1 on error.
     */
    public static int value(String s) {
      return codes.getValue(s);
    }
  }

  private final int code;

  /** Creates an option with the given option code and data. */
  public EDNSOption(int code) {
    this.code = Record.checkU16("code", code);
  }

  @Override
  public String toString() {
    return "{" + Code.string(code) + ": " + optionToString() + "}";
  }

  /**
   * Returns the EDNS Option's code.
   *
   * @return the option code
   */
  public int getCode() {
    return code;
  }

  /**
   * Returns the EDNS Option's data, as a byte array.
   *
   * @return the option data
   */
  byte[] getData() {
    DNSOutput out = new DNSOutput();
    optionToWire(out);
    return out.toByteArray();
  }

  /**
   * Converts the wire format of an EDNS Option (the option data only) into the type-specific
   * format.
   *
   * @param in The input Stream.
   */
  abstract void optionFromWire(DNSInput in) throws IOException;

  /**
   * Converts the wire format of an EDNS Option (including code and length) into the type-specific
   * format.
   *
   * @param in The input stream.
   */
  static EDNSOption fromWire(DNSInput in) throws IOException {
    int code;
    int length;

    code = in.readU16();
    length = in.readU16();
    if (in.remaining() < length) {
      throw new WireParseException("truncated option");
    }
    int save = in.saveActive();
    in.setActive(length);
    EDNSOption option;
    switch (code) {
      case Code.NSID:
        option = new NSIDOption();
        break;
      case Code.CLIENT_SUBNET:
        option = new ClientSubnetOption();
        break;
      case Code.DAU:
      case Code.DHU:
      case Code.N3U:
        option = new DnssecAlgorithmOption(code);
        break;
      case Code.COOKIE:
        option = new CookieOption();
        break;
      case Code.TCP_KEEPALIVE:
        option = new TcpKeepaliveOption();
        break;
      case Code.EDNS_EXTENDED_ERROR:
        option = new ExtendedErrorCodeOption();
        break;
      default:
        option = new GenericEDNSOption(code);
        break;
    }
    option.optionFromWire(in);
    in.restoreActive(save);

    return option;
  }

  /**
   * Converts the wire format of an EDNS Option (including code and length) into the type-specific
   * format.
   *
   * @return The option, in wire format.
   */
  public static EDNSOption fromWire(byte[] b) throws IOException {
    return fromWire(new DNSInput(b));
  }

  /**
   * Converts an EDNS Option (the type-specific option data only) into wire format.
   *
   * @param out The output stream.
   */
  abstract void optionToWire(DNSOutput out);

  /**
   * Converts an EDNS Option (including code and length) into wire format.
   *
   * @param out The output stream.
   */
  void toWire(DNSOutput out) {
    out.writeU16(code);
    int lengthPosition = out.current();
    out.writeU16(0); /* until we know better */
    optionToWire(out);
    int length = out.current() - lengthPosition - 2;
    out.writeU16At(length, lengthPosition);
  }

  /**
   * Converts an EDNS Option (including code and length) into wire format.
   *
   * @return The option, in wire format.
   */
  public byte[] toWire() {
    DNSOutput out = new DNSOutput();
    toWire(out);
    return out.toByteArray();
  }

  /**
   * Determines if two EDNS Options are identical.
   *
   * @param arg The option to compare to
   * @return true if the options are equal, false otherwise.
   */
  @Override
  public boolean equals(Object arg) {
    if (!(arg instanceof EDNSOption)) {
      return false;
    }
    EDNSOption opt = (EDNSOption) arg;
    if (code != opt.code) {
      return false;
    }
    return Arrays.equals(getData(), opt.getData());
  }

  /** Generates a hash code based on the EDNS Option's data. */
  @Override
  public int hashCode() {
    byte[] array = getData();
    int hashval = 0;
    for (byte b : array) {
      hashval += (hashval << 3) + (b & 0xFF);
    }
    return hashval;
  }

  abstract String optionToString();
}
